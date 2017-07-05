package com.github.syominsergey.spent_resources_analyzer.sources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * Created by Sergey on 28.06.2017.
 */
public class ActivityReaderImpl implements ActivityReader {

    NoteReader noteReader;
    String attributeBlockBeginMarker;
    String attributeBlockEndMarker;
    String attributesSep;
    List<String> attributeNameValueSeparators;
    String subCatSep;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public ActivityReaderImpl(
            NoteReader noteReader,
            List<AttributeMeta<?>> attributeMetas,
            String attributeBlockBeginMarker,
            String attributeBlockEndMarker,
            String attributesSep,
            String attributeNameValueSeparator,
            String subCatSep
    ) {
        this(
                noteReader,
                attributeMetas,
                attributeBlockBeginMarker,
                attributeBlockEndMarker,
                attributesSep,
                Collections.singletonList(attributeNameValueSeparator),
                subCatSep
        );
    }

    public ActivityReaderImpl(
            NoteReader noteReader,
            List<AttributeMeta<?>> attributeMetas,
            String attributeBlockBeginMarker,
            String attributeBlockEndMarker,
            String attributesSep,
            List<String> attributeNameValueSeparators,
            String subCatSep
    ) {
        this.noteReader = noteReader;
        this.attributeBlockBeginMarker = attributeBlockBeginMarker;
        this.attributeBlockEndMarker = attributeBlockEndMarker;
        this.attributesSep = attributesSep;
        if(attributeNameValueSeparators == null){
            throw new NullPointerException("Параметр attributeNameValueSeparators не должен быть null!");
        }
        if (attributeNameValueSeparators.isEmpty()) {
            throw new IllegalArgumentException("Список attributeNameValueSeparators должен содержать хотя бы 1 элемент, но он пуст!");
        }
        this.attributeNameValueSeparators = attributeNameValueSeparators;
        this.subCatSep = subCatSep;
        for (AttributeMeta<?> attributeMeta : attributeMetas) {
            attributeContexts.put(
                    attributeMeta.getName(),
                    AttributeContext.create(attributeMeta)
            );
        }
    }

    private static class AttributeContext<T> {
        public AttributeMeta<T> attributeMeta;
        public Parser<T> parser;

        public static <T> AttributeContext<T> create(AttributeMeta<T> attributeMeta){
            return new AttributeContext<T>(attributeMeta, attributeMeta.createParser());
        }

        public AttributeContext(AttributeMeta<T> attributeMeta, Parser<T> parser) {
            this.attributeMeta = attributeMeta;
            this.parser = parser;
        }

        public Attribute<T> parse(String s){
            T value = parser.parse(s);
            return new AttributeImpl<T>(value, attributeMeta);
        }
    }

    Map<String, AttributeContext<?>> attributeContexts = new HashMap<String, AttributeContext<?>>();;
    NoteImpl note = new NoteImpl();

    public boolean readNextActivity(Activity activity) throws IOException {
        if (!noteReader.readNextNote(note)) {
            return false;
        }
        ArrayList<Attribute<?>> attributes = new ArrayList<Attribute<?>>();
        String activityTitle = decodeTitle(note, attributes);
        List<List<String>> categories = decodeCategories(note);
        activity.setTitle(activityTitle);
        activity.setAttributes(attributes);
        activity.setCategories(categories);
        return true;
    }

    protected String decodeTitle(NoteImpl note, List<Attribute<?>> attributesOut){
        attributesOut.clear();
        String noteTitle = note.getTitle();
        int attributeBlockBeginPos = noteTitle.indexOf(attributeBlockBeginMarker);
        if (attributeBlockBeginPos == -1) {
            return noteTitle;
        }
        String activityTitle = noteTitle.substring(0, attributeBlockBeginPos);
        int attributeBlockEndPos = noteTitle.indexOf(attributeBlockEndMarker, attributeBlockBeginPos + attributeBlockBeginMarker.length());
        if (attributeBlockEndPos == -1) {
            attributeBlockEndPos = noteTitle.length();
        }
        String attributesSubstring = noteTitle.substring(
                attributeBlockBeginPos + attributeBlockBeginMarker.length(),
                attributeBlockEndPos
        );
        String[] attributeStrings = attributesSubstring.split(attributesSep);
        for (int i = 0; i < attributeStrings.length; i++) {
            String attributeString = attributeStrings[i];
            String[] nameValue = null;
            for (String attributeNameValueSeparator : attributeNameValueSeparators) {
                nameValue = attributeString.split(attributeNameValueSeparator);
                if(nameValue.length == 2){
                    break;
                }
            }
            if(nameValue.length != 2){
                LOG.warn(
                        "При разборе заголовка '{}' обнаружена строка атрибута '{}', которая не разделяется с помощью " +
                                "сепараторов '{}' на имя и значение, т.к. в результате имеем кол-во элементов, отличное от 2",
                        noteTitle,
                        attributeString,
                        attributeNameValueSeparators,
                        nameValue.length
                );
                continue;
            }
            String attributeName = nameValue[0];
            String attributeValueString = nameValue[1];
            AttributeContext<?> attributeContext = attributeContexts.get(attributeName);
            if(attributeContext == null){
                LOG.warn(
                        "При разборе заголовка '{}' обнаружена строка атрибута '{}', которая с помощью" +
                                " сепаратора '{}' даёт имя '{}', которого нет в списке имён атрибутов '{}'",
                        noteTitle,
                        attributeString,
                        attributesSep,
                        attributeName,
                        attributeContexts.keySet()
                );
                continue;
            }
            Attribute<?> attribute;
            try {
                attribute = attributeContext.parse(attributeValueString);
            } catch (Throwable e) {
                String s = String.format(
                        "Ошибка при разборе значения '%s' атрибута с именем '%s'",
                        attributeValueString,
                        attributeName
                );
                LOG.warn(s, e);
                continue;
            }
            attributesOut.add(attribute);
        }
        return activityTitle;
    }

    protected List<List<String>> decodeCategories(NoteImpl note){
        List<String> tags = note.getTags();
        List<List<String>> categories = new ArrayList<>(tags.size());
        for (String tag : tags) {
            String[] category = tag.split(subCatSep);
            categories.add(new ArrayList<>(Arrays.asList(category)));
        }
        return categories;
    }

    /**
     * Created by Sergey on 28.06.2017.
     */
    public static class NoteImpl implements Note {

        String title;

        public void setTitle(String title) {
            this.title = title;
        }

        List<String> tags;

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public String getTitle() {
            return title;
        }

        public List<String> getTags() {
            return tags;
        }
    }

    /**
     * Created by Sergey on 28.06.2017.
     */
    public static class AttributeImpl<T> implements Attribute<T> {

        protected T value;
        protected AttributeMeta<T> meta;

        public AttributeImpl(T value, AttributeMeta<T> meta) {
            this.value = value;
            this.meta = meta;
        }

        public T getValue() {
            return null;
        }

        public AttributeMeta<T> getMeta() {
            return null;
        }

        @Override
        public String toString() {
            return "AttributeImpl{" +
                    "value=" + value +
                    ", meta=" + meta +
                    '}';
        }
    }
}
