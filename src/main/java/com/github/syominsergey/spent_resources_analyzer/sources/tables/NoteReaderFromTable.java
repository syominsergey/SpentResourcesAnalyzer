package com.github.syominsergey.spent_resources_analyzer.sources.tables;

import com.github.syominsergey.spent_resources_analyzer.sources.Note;
import com.github.syominsergey.spent_resources_analyzer.sources.NoteReader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Читатель заметок из табличного формата
 * Created by Sergey on 26.06.2017.
 */
public class NoteReaderFromTable implements NoteReader {

    protected TupleReader tupleReader;
    protected Map<Integer, NoteFieldDecoder> fieldEncoders;

    public NoteReaderFromTable(TupleReader tableReader, Map<Integer, NoteFieldDecoder> fieldEncoders) {
        this.tupleReader = tableReader;
        this.fieldEncoders = fieldEncoders;
    }

    public NoteReaderFromTable(
            TupleReader tupleReader,
            final int titlePos,
            final int tagsPos,
            final String tagsDelim
    ) {
        this(
            tupleReader,
            new HashMap<Integer, NoteFieldDecoder>(
                new HashMap<Integer, NoteFieldDecoder>(){{
                    put(titlePos, new TitleDecoder());
                    put(tagsPos, new TagsDecoder(tagsDelim));
                }}
            )
        );
    }

    protected List<String> tuple = new ArrayList<String>();

    public boolean readNextNote(Note note) throws IOException {
        if(!tupleReader.readNextTuple(tuple)){
            return false;
        }
        for (Map.Entry<Integer, NoteFieldDecoder> entry : fieldEncoders.entrySet()) {
            int fieldNum = entry.getKey();
            NoteFieldDecoder fieldEncoder = entry.getValue();
            String encodedField = tuple.get(fieldNum);
            fieldEncoder.writeField(encodedField, note);
        }
        return true;
    }
}
