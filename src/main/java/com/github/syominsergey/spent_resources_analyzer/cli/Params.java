package com.github.syominsergey.spent_resources_analyzer.cli;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 31.07.2017.
 */
public class Params {

    @Option(name="--source", usage="source tsv-file, exported from Evernote", required = true)
    public String sourceFile;

    @Option(name="--attribute", usage = "attribute to analyze", required = true)
    public String attributeName;

    @Option(name="--area", usage = "subcategory to analyze", required = true)
    public String area;

    @Option(name="--include", usage = "subcategory to include only")
    public String include = null;

    @Option(name="--exclude", usage = "subcategory to exclude")
    public String exclude = null;

    @Argument
    private List<String> arguments = new ArrayList<>();

}
