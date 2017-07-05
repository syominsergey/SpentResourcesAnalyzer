package com.github.syominsergey.spent_resources_analyzer.model;

import com.github.syominsergey.spent_resources_analyzer.sources.ActivityReader;

/**
 * Created by Sergey on 03.07.2017.
 */
public interface Model {
    void loadActivitiesFrom(ActivityReader activityReader);
}
