package com.github.syominsergey.spent_resources_analyzer.model;

/**
 * Created by Sergey on 27.07.2017.
 */
public class InspectorWall implements ActivityInspector {

    protected ActivityInspector implInspector;
    protected ActivityChecker activityChecker;

    public InspectorWall(ActivityInspector implInspector, ActivityChecker activityChecker) {
        this.implInspector = implInspector;
        this.activityChecker = activityChecker;
    }

    @Override
    public void inspectNextActivity(Activity activity) {
        if(activityChecker.checkActivity(activity)){
            implInspector.inspectNextActivity(activity);
        }
    }
}
