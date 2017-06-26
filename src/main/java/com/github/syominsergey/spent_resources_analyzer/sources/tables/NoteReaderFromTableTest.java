package com.github.syominsergey.spent_resources_analyzer.sources.tables;

import com.github.syominsergey.spent_resources_analyzer.sources.Note;
import com.github.syominsergey.spent_resources_analyzer.sources.NoteReader;
import com.github.syominsergey.spent_resources_analyzer.sources.tables.NoteReaderFromTable;
import com.github.syominsergey.spent_resources_analyzer.sources.tables.TupleReaderFromTsv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by Sergey on 26.06.2017.
 */
public class NoteReaderFromTableTest {

    public static void main(String[] args) throws IOException {

        Note note = new Note() {
            String title;
            public void setTitle(String title) {
                this.title = title;
            }
            List<String> tags;
            public void setTags(List<String> tags) {
                this.tags = tags;
            }
            @Override
            public String toString() {
                return "Note{" +
                        "title='" + title + '\'' +
                        ", tags=" + tags +
                        '}';
            }
        };

        String inputFileName = "sample.tsv";
        NoteReader noteReader = new NoteReaderFromTable(
                new TupleReaderFromTsv(
                        new BufferedReader(
                                new FileReader(inputFileName)
                        ),
                        "\t"
                ),
                1,
                2,
                ", "
        );
        while (noteReader.readNextNote(note)){
            System.out.println(note);
        }
    }


}
