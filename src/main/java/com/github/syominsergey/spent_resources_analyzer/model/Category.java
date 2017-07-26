package com.github.syominsergey.spent_resources_analyzer.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergey on 04.07.2017.
 */
class Category implements InspectorConductor {

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
    public void conduct(ActivityInspector inspector) {
        for (Category category : subCategories.values()) {
            category.conduct(inspector);
        }
        for (Activity activity : activities) {
            inspector.inspectNextActivity(activity);
        }
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                '}';
    }
}
