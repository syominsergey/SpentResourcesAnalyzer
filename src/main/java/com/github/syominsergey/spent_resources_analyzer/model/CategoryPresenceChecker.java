package com.github.syominsergey.spent_resources_analyzer.model;

/**
 * Created by Sergey on 27.07.2017.
 */
public class CategoryPresenceChecker implements ActivityChecker{

    protected Category wantedCategory;

    public CategoryPresenceChecker(Category wantedCategory) {
        this.wantedCategory = wantedCategory;
    }

    @Override
    public boolean checkActivity(Activity activity) {
        for (Category category : activity.categories) {
            if(checkPresence(category)){
                return true;
            }
        }
        return false;
    }

    protected boolean checkPresence(Category category){
        while (category != null){
            if(category != wantedCategory){
                category = category.superCategory;
            } else {
                return true;
            }
        }
        return false;
    }
}
