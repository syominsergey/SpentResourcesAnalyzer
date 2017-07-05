package com.github.syominsergey.spent_resources_analyzer.model;

import com.github.syominsergey.spent_resources_analyzer.sources.Activity;
import com.github.syominsergey.spent_resources_analyzer.sources.ActivityReader;
import com.github.syominsergey.spent_resources_analyzer.sources.Attribute;

import java.util.List;
import java.util.Map;

/**
 * Created by Sergey on 03.07.2017.
 */
public class ModelImpl implements Model {

    private static class ActivityBlank implements com.github.syominsergey.spent_resources_analyzer.sources.Activity {

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
    }

    @Override
    public void loadActivitiesFrom(ActivityReader activityReader) {

    }
}
