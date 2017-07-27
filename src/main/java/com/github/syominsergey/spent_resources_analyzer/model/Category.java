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

    public <T> T computeAttributeSum(AttributeMeta<T> attributeMeta){
        return computeAttributeSum(attributeMeta, null);
    }

    public <T> T computeAttributeSum(AttributeMeta<T> attributeMeta, ActivityChecker activityChecker){
        Acc<T> attributeAcc = attributeMeta.createAcc();
        String attributeName = attributeMeta.getName();
        ActivityInspector activityInspector = new AttributeSummator(attributeName, attributeAcc);
        if (activityChecker != null) {
            activityInspector = new InspectorWall(activityInspector, activityChecker);
        }
        conduct(activityInspector);
        return attributeAcc.getSum();
    }

    public Category getSubCategory(List<String> subCategoryName){
        Category curCategory = this;
        for (String s : subCategoryName) {
            Category subCategory = curCategory.subCategories.get(s);
            if(subCategory == null){
                return null;
            }
            curCategory = subCategory;
        }
        return curCategory;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                '}';
    }
}
