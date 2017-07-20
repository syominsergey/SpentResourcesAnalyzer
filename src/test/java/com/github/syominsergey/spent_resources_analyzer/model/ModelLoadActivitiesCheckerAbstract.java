package com.github.syominsergey.spent_resources_analyzer.model;

import com.github.syominsergey.spent_resources_analyzer.sources.ActivityImpl;

import java.util.List;
import java.util.Map;

/**
 * Created by Sergey on 21.07.2017.
 */
public abstract class ModelLoadActivitiesCheckerAbstract {

    public ModelLoadActivitiesCheckerAbstract(
            List<ActivityImpl> sourceActivities,
            Map<List<String>, List<Activity>> arrangedActivities
    ) {

    }

    protected abstract Model createModel();

    public void check() throws Exception {

    }

}
