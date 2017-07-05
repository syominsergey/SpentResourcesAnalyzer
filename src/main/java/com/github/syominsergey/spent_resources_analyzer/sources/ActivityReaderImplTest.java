package com.github.syominsergey.spent_resources_analyzer.sources;

import com.github.syominsergey.spent_resources_analyzer.sources.tables.NoteReaderFromTable;
import com.github.syominsergey.spent_resources_analyzer.sources.tables.TupleReaderFromTsv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sergey on 29.06.2017.
 */
public class ActivityReaderImplTest {

    //for log4j configuration pass vm param: -Dlog4j.configuration="file:log4j.xml"
    private static final Logger LOG = LoggerFactory.getLogger(ActivityReaderImplTest.class);

    public static void main(String[] args) throws IOException {
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
        List<AttributeMeta<?>> attributeMetas = new ArrayList<>();
        attributeMetas.add(new MoneyAttributeMeta());
        attributeMetas.add(new TimeAttributeMeta());
        ActivityReader activityReader = new ActivityReaderImpl(
                noteReader,
                attributeMetas,
                " (",
                ")",
                ", ",
                Arrays.asList(": ", " "),
                "-"
        );
        Activity activity = new ActivityImpl();
        while (activityReader.readNextActivity(activity)){
            LOG.debug(activity.toString());
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
