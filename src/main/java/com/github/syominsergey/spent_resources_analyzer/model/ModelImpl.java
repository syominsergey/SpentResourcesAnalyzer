package com.github.syominsergey.spent_resources_analyzer.model;

import com.github.syominsergey.spent_resources_analyzer.sources.ActivityImpl;
import com.github.syominsergey.spent_resources_analyzer.sources.ActivityReader;
import com.github.syominsergey.spent_resources_analyzer.sources.Attribute;
import com.github.syominsergey.spent_resources_analyzer.sources.AttributeMeta;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public <T> Map<String, T> computeAttributeSumBySubCategories(
            AttributeMeta<T> attributeMeta,
            List<String> subCategoryToAnalyze,
            ActivityFilterBySubcatPresence activityFilter
    ){
        ActivityChecker activityChecker;
        if(activityFilter != null){
            Category filteredCategory = rootCategory.getSubCategory(activityFilter.subCategoryName);
            if(filteredCategory == null){
                String message = String.format(
                        "В модели не удалось найти подкатегорию с именем %s, которая требуется в условии поиска",
                        activityFilter.subCategoryName
                );
                throw new RuntimeException(message);
            }
            activityChecker = new CategoryPresenceChecker(filteredCategory);
            if(activityFilter.mode == ActivityFilterBySubcatPresence.Mode.EXCLUDE){
                activityChecker = new InvertingChecker(activityChecker);
            }
        } else {
            activityChecker = null;
        }
        Category targetCategory = rootCategory.getSubCategory(subCategoryToAnalyze);
        if(targetCategory == null){
            String message = String.format(
                    "В модели не удалось найти подкатегорию с именем %s, в которой планировался анализ атрибутов",
                    subCategoryToAnalyze
            );
            throw new RuntimeException(message);
        }
        HashMap<String, T> result = new HashMap<>(targetCategory.subCategories.size());
        for (Map.Entry<String, Category> entry : targetCategory.subCategories.entrySet()) {
            String categoryName = entry.getKey();
            Category categoryRef = entry.getValue();
            T sumForSubCategory = categoryRef.computeAttributeSum(attributeMeta, activityChecker);
            result.put(categoryName, sumForSubCategory);
        }
        return result;
    }

}
