package com.github.syominsergey.spent_resources_analyzer.sources.tables;

import com.github.syominsergey.spent_resources_analyzer.sources.Note;

/**
 * Декодер заголовка
 * Created by Sergey on 26.06.2017.
 */
public class TitleDecoder implements NoteFieldDecoder {
    public void writeField(String encodedField, Note note) {
        note.setTitle(encodedField);
    }
}
