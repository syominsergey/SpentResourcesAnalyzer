package com.github.syominsergey.spent_resources_analyzer.model;

import com.github.syominsergey.spent_resources_analyzer.sources.ActivityImpl;
import org.junit.Assert;

import java.util.*;

/**
 * Created by Sergey on 21.07.2017.
 */
public class ModelLoadActivitiesChecker {

    protected List<ActivityImpl> sourceActivities;
    protected Map<List<String>, List<Activity>> arrangedActivities;

    public ModelLoadActivitiesChecker(
            List<ActivityImpl> sourceActivities,
            Map<List<String>, List<Activity>> arrangedActivities
    ) {
        this.sourceActivities = sourceActivities;
        this.arrangedActivities = arrangedActivities;
    }

    public void check() throws Exception {
        ModelImpl model = new ModelImpl();
        ActivityReaderFromList activityReader = new ActivityReaderFromList(sourceActivities.iterator());
        model.loadActivitiesFrom(activityReader);
        List<String> curCategoryPath = new ArrayList<>();
        checkRec(model.rootCategory, curCategoryPath);
        Assert.assertEquals(
                "Не должно быть ожидаемых категорий, котрые не были посещены при обходе построенного дерева категорий",
                Collections.EMPTY_MAP,
                arrangedActivities
        );
    }

    protected void checkRec(Category curCategoryRef, List<String> curCategoryPath){
        List<Activity> activities = arrangedActivities.remove(curCategoryPath);
        if (activities != null) {
            Set<ActivityAdapter> expectedActivities = new HashSet<>(activities.size());
            activities.forEach(
                    activity -> expectedActivities.add(new ActivityAdapter(activity, true))
            );
            Set<ActivityAdapter> actualActivities = new HashSet<>(curCategoryRef.activities.size());
            curCategoryRef.activities.forEach(
                    activity -> actualActivities.add(new ActivityAdapter(activity, false))
            );
            //Set<ActivityAdapter> intersection = new HashSet<>(expectedActivities);
            //intersection.retainAll(actualActivities);
            //Set<ActivityAdapter> union = new HashSet<>(expectedActivities);
            //union.addAll(actualActivities);
            //Set<ActivityAdapter> symmetricDiff = new HashSet<>(union);
            //symmetricDiff.removeAll(intersection);
            String message = String.format(
                    "Множества ожидаемых и фактических активностей для категории %s должны совпадать",
                    curCategoryPath
            );
            Assert.assertEquals(
                    message,
                    expectedActivities,
                    actualActivities
            );
        } else {
            String message = String.format(
                    "У текущей категории %s с путём %s не должно быть активностей",
                    curCategoryRef,
                    curCategoryPath
            );
            Assert.assertEquals(
                    message,
                    Collections.EMPTY_LIST,
                    curCategoryRef.activities
            );
        }
        for (Map.Entry<String, Category> entry : curCategoryRef.subCategories.entrySet()) {
            String subCategoryName = entry.getKey();
            Category subCategoryRef = entry.getValue();
            curCategoryPath.add(subCategoryName);
            try {
                checkRec(subCategoryRef, curCategoryPath);
            } finally {
                curCategoryPath.remove(curCategoryPath.size() - 1);
            }
        }
    }

}
