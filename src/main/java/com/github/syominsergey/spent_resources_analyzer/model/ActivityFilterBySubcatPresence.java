package com.github.syominsergey.spent_resources_analyzer.model;

import java.util.List;

/**
 * Created by Sergey on 28.07.2017.
 */
public class ActivityFilterBySubcatPresence {

    public enum Mode {
        INCLUDE, EXCLUDE,
    }

    public List<String> subCategoryName;
    public Mode mode;

    public ActivityFilterBySubcatPresence(List<String> subCategoryName, Mode mode) {
        this.subCategoryName = subCategoryName;
        this.mode = mode;
    }

    public ActivityFilterBySubcatPresence() {
    }
}
