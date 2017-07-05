package com.github.syominsergey.spent_resources_analyzer.model;

import com.github.syominsergey.spent_resources_analyzer.sources.Acc;
import com.github.syominsergey.spent_resources_analyzer.sources.AttributeMeta;

import java.util.List;
import java.util.Map;

/**
 * Created by Sergey on 04.07.2017.
 */
class Category implements AttributeAggregator {

    String name;

    Category superCategory;

    Map<String, Category> subCategories;

    List<Activity> activities;

    @Override
    public void aggregate(String attributeName, Acc<?> attributeAcc) {
        for (Category category : subCategories.values()) {
            category.aggregate(attributeName, attributeAcc);
        }
        for (Activity activity : activities) {
            activity.aggregate(attributeName, attributeAcc);
        }
    }
}
