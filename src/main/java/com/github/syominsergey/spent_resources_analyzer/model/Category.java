package com.github.syominsergey.spent_resources_analyzer.model;

import com.github.syominsergey.spent_resources_analyzer.sources.Acc;
import com.github.syominsergey.spent_resources_analyzer.sources.AttributeMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergey on 04.07.2017.
 */
class Category implements AttributeAggregator {

    public Category(String name, Category superCategory) {
        this.name = name;
        this.superCategory = superCategory;
        this.subCategories = new HashMap<>();
        this.activities = new ArrayList<>();
    }

    public Category() {
        this("root", null);
    }

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
