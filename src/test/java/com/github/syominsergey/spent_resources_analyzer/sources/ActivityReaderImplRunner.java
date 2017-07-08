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
public class ActivityReaderImplRunner {

    //for log4j configuration pass vm param: -Dlog4j.configuration="file:log4j.xml"
    private static final Logger LOG = LoggerFactory.getLogger(ActivityReaderImplRunner.class);

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
        AttributesReader attributesReader = new NamedAttributesReader(
                attributeMetas,
                ", ",
                Arrays.asList(": ", " ")
        );
        ActivityReader activityReader = new ActivityReaderImpl(
                noteReader,
                attributesReader,
                " (",
                ")",
                "-"
        );
        Activity activity = new ActivityImpl();
        while (activityReader.readNextActivity(activity)){
            LOG.debug(activity.toString());
        }
    }

}
