package com.github.syominsergey.spent_resources_analyzer.sources.tables;

import java.io.IOException;
import java.util.List;

/**
 * Класс для последовательного чтения кортежей строк
 * Created by Sergey on 26.06.2017.
 */
public interface TupleReader {

    /**
     * Прочитать очередной кортеж строк и сохранить его в заданном объекте
     * @param tuple список, в который будет записан кортеж строк
     * @return true, если кортеж строк удалось записать и false, если кортежи строк кончились
     */
    boolean readNextTuple(List<String> tuple) throws IOException;
}
