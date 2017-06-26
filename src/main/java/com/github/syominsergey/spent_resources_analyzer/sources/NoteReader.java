package com.github.syominsergey.spent_resources_analyzer.sources;

import java.io.IOException;

/**
 * Интерфейс читателя заметок
 * Created by Sergey on 26.06.2017.
 */
public interface NoteReader {
    /**
     * Заполняет очередную заметку
     * @param note объект, который будет заполнен данными очередной заметки
     * @return true, если заметку удалось заполнить данными, false, если заметки в источнике кончились
     */
    boolean readNextNote(Note note) throws IOException;
}
