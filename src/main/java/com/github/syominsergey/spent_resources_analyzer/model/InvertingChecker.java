package com.github.syominsergey.spent_resources_analyzer.model;

/**
 * Created by Sergey on 27.07.2017.
 */
public class InvertingChecker implements ActivityChecker {

    protected ActivityChecker impl;

    public InvertingChecker(ActivityChecker activityChecker) {
        this.impl = activityChecker;
    }

    @Override
    public boolean checkActivity(Activity activity) {
        return !impl.checkActivity(activity);
    }
}
