package com.github.syominsergey.spent_resources_analyzer.sources.tables;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * Читатель таблиц из TSV-формата
 * Created by Sergey on 26.06.2017.
 */
public class TupleReaderFromTsv implements TupleReader {

    protected String fieldsDelim;
    protected BufferedReader reader;

    public TupleReaderFromTsv(BufferedReader reader, String fieldsDelim) throws IOException {
        this.fieldsDelim = fieldsDelim;
        this.reader = reader;
        //пропуск заголовка
        reader.readLine();
    }

    public TupleReaderFromTsv(BufferedReader reader) throws IOException {
        this(reader, "\t");
    }

    public boolean readNextTuple(List<String> tuple) throws IOException {
        String nextLine = reader.readLine();
        if(nextLine == null) {
            return false;
        }
        String[] fieldsArray = nextLine.split(fieldsDelim);
        tuple.clear();
        for (int i = 0; i < fieldsArray.length; i++) {
            tuple.add(fieldsArray[i]);
        }
        if(nextLine.endsWith(fieldsDelim)){
            tuple.add("");
        }
        return true;
    }

}
