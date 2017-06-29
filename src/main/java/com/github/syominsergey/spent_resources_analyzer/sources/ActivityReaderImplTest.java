package com.github.syominsergey.spent_resources_analyzer.sources;

import com.github.syominsergey.spent_resources_analyzer.sources.tables.NoteReaderFromTable;
import com.github.syominsergey.spent_resources_analyzer.sources.tables.TupleReaderFromTsv;
import org.apache.log4j.xml.DOMConfigurator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 29.06.2017.
 */
public class ActivityReaderImplTest {

    public static void main(String[] args) throws IOException {
        DOMConfigurator.configure("log4j.xml");
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
        List<AttributeType<?>> attributeTypes = new ArrayList<>();
        attributeTypes.add(new MoneyAttributeType());
        attributeTypes.add(new TimeAttributeType());
        ActivityReader activityReader = new ActivityReaderImpl(
                noteReader,
                attributeTypes,
                " (",
                ")",
                ", ",
                ": ",
                "-"
        );
        Activity activity = new ActivityImpl();
        while (activityReader.readNextActivity(activity)){
            System.out.println(activity.toString());
        }
    }

    private static class ActivityImpl implements Activity {

        String title;

        @Override
        public void setTitle(String title) {
            this.title = title;
        }

        List<Attribute<?>> attributes;

        @Override
        public void setAttributes(List<Attribute<?>> attributes) {
            this.attributes = attributes;
        }

        List<List<String>> categories;

        @Override
        public void setCategories(List<List<String>> categories) {
            this.categories = categories;
        }

        @Override
        public String toString() {
            return "ActivityImpl{\n" +
                    "\ttitle='" + title + "\'\n" +
                    "\tattributes=" + attributes + "\n" +
                    "\tcategories=" + categories + "\n" +
                    '}';
        }
    }
}
