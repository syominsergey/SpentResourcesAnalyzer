package com.github.syominsergey.spent_resources_analyzer.sources.tables;

import com.github.syominsergey.spent_resources_analyzer.sources.Note;

/**
 * Интерфейс декодера полей заметки
 * Created by Sergey on 26.06.2017.
 */
public interface NoteFieldDecoder {
    /**
     * Записывает информацию, закодированную в строковом представлении значения поля, в соответствующее
     * поле заметки
     * @param encodedField заколированное значение поля
     * @param note заметка, куда должно быть записано поле
     */
    void writeField(String encodedField, Note note);
}
