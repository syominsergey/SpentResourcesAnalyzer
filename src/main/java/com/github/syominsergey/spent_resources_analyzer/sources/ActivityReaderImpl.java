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
    AttributesReader attributesReader;
    String attributeBlockBeginMarker;
    String attributeBlockEndMarker;
    String subCatSep;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public ActivityReaderImpl(
            NoteReader noteReader,
            AttributesReader attributesReader,
            String attributeBlockBeginMarker,
            String attributeBlockEndMarker,
            String subCatSep
    ) {
        this.noteReader = noteReader;
        this.attributesReader = attributesReader;
        this.attributeBlockBeginMarker = attributeBlockBeginMarker;
        this.attributeBlockEndMarker = attributeBlockEndMarker;
        this.subCatSep = subCatSep;
    }

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
        attributesReader.readAttributes(attributesSubstring, attributesOut);
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

}
