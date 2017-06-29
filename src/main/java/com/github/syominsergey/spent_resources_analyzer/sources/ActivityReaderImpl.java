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
    String attributeNameValueSep;
    String subCatSep;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public ActivityReaderImpl(
            NoteReader noteReader,
            List<AttributeType<?>> attributeTypes,
            String attributeBlockBeginMarker,
            String attributeBlockEndMarker,
            String attributesSep,
            String attributeNameValueSep,
            String subCatSep
    ) {
        this.noteReader = noteReader;
        this.attributeBlockBeginMarker = attributeBlockBeginMarker;
        this.attributeBlockEndMarker = attributeBlockEndMarker;
        this.attributesSep = attributesSep;
        this.attributeNameValueSep = attributeNameValueSep;
        this.subCatSep = subCatSep;
        for (AttributeType<?> attributeType : attributeTypes) {
            attributeContexts.put(
                    attributeType.getName(),
                    AttributeContext.create(attributeType)
            );
        }
    }

    private static class AttributeContext<T> {
        public AttributeType<T> attributeType;
        public Parser<T> parser;

        public static <T> AttributeContext<T> create(AttributeType<T> attributeType){
            return new AttributeContext<T>(attributeType, attributeType.createParser());
        }

        public AttributeContext(AttributeType<T> attributeType, Parser<T> parser) {
            this.attributeType = attributeType;
            this.parser = parser;
        }

        public Attribute<T> parse(String s){
            T value = parser.parse(s);
            return new AttributeImpl<T>(value, attributeType);
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
            String[] nameValue = attributeString.split(attributeNameValueSep);
            if(nameValue.length != 2){
                LOG.warn(
                        "При разборе заголовка '{}' обнаружена строка атрибута '{}', которая не разделяется с помощью " +
                                "сепаратора '{}' на имя и значение, т.к. в результате имеем {} элемент(а/ов)",
                        noteTitle,
                        attributeString,
                        attributeNameValueSep,
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
        protected AttributeType<T> type;

        public AttributeImpl(T value, AttributeType<T> type) {
            this.value = value;
            this.type = type;
        }

        public T getValue() {
            return null;
        }

        public AttributeType<T> getType() {
            return null;
        }

        @Override
        public String toString() {
            return "AttributeImpl{" +
                    "value=" + value +
                    ", type=" + type +
                    '}';
        }
    }
}
