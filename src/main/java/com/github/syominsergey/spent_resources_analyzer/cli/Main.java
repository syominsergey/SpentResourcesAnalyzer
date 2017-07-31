package com.github.syominsergey.spent_resources_analyzer.cli;

import com.github.syominsergey.spent_resources_analyzer.model.ActivityFilterBySubcatPresence;
import com.github.syominsergey.spent_resources_analyzer.model.Model;
import com.github.syominsergey.spent_resources_analyzer.model.ModelImpl;
import com.github.syominsergey.spent_resources_analyzer.sources.*;
import com.github.syominsergey.spent_resources_analyzer.sources.Formatter;
import com.github.syominsergey.spent_resources_analyzer.sources.tables.NoteReaderFromTable;
import com.github.syominsergey.spent_resources_analyzer.sources.tables.TupleReaderFromTsv;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Sergey on 31.07.2017.
 */
public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    protected static <T> void helper(AttributeMeta<T> attributeMeta, Params params, Model model, String subCatSep){
        List<String> subCat = Arrays.asList(params.area.split(subCatSep));
        ActivityFilterBySubcatPresence activityFilter;
        if(params.include != null){
            List<String> subCatToIncludeOnly = Arrays.asList(params.include.split(subCatSep));
            activityFilter = new ActivityFilterBySubcatPresence(
                    subCatToIncludeOnly, ActivityFilterBySubcatPresence.Mode.INCLUDE
            );
        } else if(params.exclude != null){
            List<String> subCatToExclude = Arrays.asList(params.exclude.split(subCatSep));
            activityFilter = new ActivityFilterBySubcatPresence(
                    subCatToExclude, ActivityFilterBySubcatPresence.Mode.EXCLUDE
            );
        } else {
            activityFilter = null;
        }
        Map<String, T> report = model.computeAttributeSumBySubCategories(attributeMeta, subCat, activityFilter);
        Formatter<T> formatter = attributeMeta.createFormatter();
        for (Map.Entry<String, T> entry : report.entrySet()) {
            String name = entry.getKey();
            T value = entry.getValue();
            String value_s = formatter.format(value);
            System.out.printf("%s: %s\n", name, value_s);
        }
    }

    public static void main(String[] args) {
        Params params = new Params();
        CmdLineParser cmdLineParser = new CmdLineParser(params);
        try {
            cmdLineParser.parseArgument(args);
        } catch (CmdLineException e) {
            LOG.error("Ошибка при разборе параметров", e);
            cmdLineParser.printUsage(System.err);
            return;
        }
        NoteReader noteReader;
        FileReader fileReader;
        try {
            fileReader = new FileReader(params.sourceFile);
            noteReader = new NoteReaderFromTable(
                    new TupleReaderFromTsv(
                            new BufferedReader(
                                    fileReader
                            ),
                            "\t"
                    ),
                    1,
                    2,
                    ", "
            );
        } catch (IOException e) {
            String msg = String.format(
                    "Ошибка при чтении входного файла \"%s\"",
                    params.sourceFile
            );
            LOG.error(msg, e);
            return;
        }
        try {
            Map<String, AttributeMeta<?>> attributeMetaMap = new HashMap<>();
            List<AttributeMeta<?>> attributeMetas = new ArrayList<>();
            MoneyAttributeMeta moneyAttributeMeta = new MoneyAttributeMeta();
            attributeMetas.add(moneyAttributeMeta);
            attributeMetaMap.put(moneyAttributeMeta.getName(), moneyAttributeMeta);
            TimeAttributeMeta timeAttributeMeta = new TimeAttributeMeta();
            attributeMetas.add(timeAttributeMeta);
            attributeMetaMap.put(timeAttributeMeta.getName(), timeAttributeMeta);
            AttributeParser<String> namedAttributesParser = new NamedAttributesParser(
                    attributeMetas,
                    Arrays.asList(": ", " ")
            );
            AttributeParser<ElementaryParser<?>> typedAttributesParser = new TypedAttributesParser(
                    attributeMetas
            );
            List<AttributeParser<?>> parsers = new ArrayList<>();
            parsers.add(namedAttributesParser);
            parsers.add(typedAttributesParser);
            AttributesReader attributesReader = new AttributesReaderImpl(
                    parsers,
                    ", "
            );
            String subCatSep = "-";
            ActivityReader activityReader = new ActivityReaderImpl(
                    noteReader,
                    attributesReader,
                    " (",
                    ")",
                    subCatSep
            );
            Model model = new ModelImpl();
            try {
                model.loadActivitiesFrom(activityReader);
            } catch (IOException e) {
                String msg = String.format(
                        "Ошибка при чтении исходного файла \"%s\"",
                        params.sourceFile
                );
                LOG.error(msg);
                return;
            }
            AttributeMeta<?> attributeMeta = attributeMetaMap.get(params.attributeName);
            if(attributeMeta == null){
                String msg = String.format(
                        "Введённое имя атрибута \"%s\" не содержится в коллекции известных атрибутов %s",
                        params.attributeName,
                        attributeMetaMap
                );
                LOG.error(msg);
                return;
            }
            helper(attributeMeta, params, model, subCatSep);
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                String msg = String.format(
                        "Ошибка при закрытии входного файла \"%s\"",
                        params.sourceFile
                );
                LOG.error(msg, e);
            }
        }
    }
}
