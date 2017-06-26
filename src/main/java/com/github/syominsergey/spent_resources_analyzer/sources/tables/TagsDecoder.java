package com.github.syominsergey.spent_resources_analyzer.sources.tables;

import com.github.syominsergey.spent_resources_analyzer.sources.Note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Декодер списка дегов, закодированных просто через разделитель
 * Created by Sergey on 26.06.2017.
 */
public class TagsDecoder implements NoteFieldDecoder {

    protected String tagsDelim;

    public TagsDecoder(String tagsDelim) {
        this.tagsDelim = tagsDelim;
    }

    public void writeField(String encodedField, Note note) {
        String[] tagArray = encodedField.split(tagsDelim);
        List<String> tagList = new ArrayList<String>(Arrays.asList(tagArray));
        note.setTags(tagList);
    }
}
