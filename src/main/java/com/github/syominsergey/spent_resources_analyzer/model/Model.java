package com.github.syominsergey.spent_resources_analyzer.model;

import com.github.syominsergey.spent_resources_analyzer.sources.ActivityReader;
import com.github.syominsergey.spent_resources_analyzer.sources.AttributeMeta;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergey on 03.07.2017.
 */
public interface Model {
    void loadActivitiesFrom(ActivityReader activityReader) throws IOException;
    <T> Map<String, T> computeAttributeSumBySubCategories(
            AttributeMeta<T> attributeMeta,
            List<String> subCategoryToAnalyze,
            ActivityFilterBySubcatPresence activityFilter
    );
}
