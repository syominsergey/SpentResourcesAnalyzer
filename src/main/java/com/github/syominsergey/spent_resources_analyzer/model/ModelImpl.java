package com.github.syominsergey.spent_resources_analyzer.model;

import com.github.syominsergey.spent_resources_analyzer.sources.ActivityImpl;
import com.github.syominsergey.spent_resources_analyzer.sources.ActivityReader;
import com.github.syominsergey.spent_resources_analyzer.sources.Attribute;

import java.io.IOException;
import java.util.List;

/**
 * Created by Sergey on 03.07.2017.
 */
public class ModelImpl implements Model {

    Category rootCategory;

    public ModelImpl() {
        rootCategory = new Category();
    }

    @Override
    public void loadActivitiesFrom(ActivityReader activityReader) throws IOException {
        ActivityImpl activity = new ActivityImpl();
        while (activityReader.readNextActivity(activity)){
            Activity activityNode = new Activity(activity.getTitle(), activity.getAttributes());
            List<List<String>> categories = activity.getCategories();
            for (List<String> category : categories) {
                Category curCategory = rootCategory;
                for (String curCategoryName : category) {
                    final Category curCategoryFinal = curCategory;
                    curCategory = curCategory.subCategories.computeIfAbsent(
                            curCategoryName,
                            (String s) -> new Category(s, curCategoryFinal)
                    );
                }
                curCategory.activities.add(activityNode);
                activityNode.categories.add(curCategory);
            }
        }
    }
}
