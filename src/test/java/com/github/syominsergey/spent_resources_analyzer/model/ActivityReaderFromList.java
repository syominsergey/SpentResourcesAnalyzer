package com.github.syominsergey.spent_resources_analyzer.model;

import com.github.syominsergey.spent_resources_analyzer.sources.*;
import com.github.syominsergey.spent_resources_analyzer.sources.Activity;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Sergey on 23.07.2017.
 */
public class ActivityReaderFromList implements ActivityReader {

    Iterator<ActivityImpl> activityIterator;

    public ActivityReaderFromList(Iterator<ActivityImpl> activityIterator) {
        this.activityIterator = activityIterator;
    }

    @Override
    public boolean readNextActivity(Activity activity) throws IOException {
        if (!activityIterator.hasNext()) {
            return false;
        }
        ActivityImpl nextActivity = activityIterator.next();
        activity.setTitle(nextActivity.getTitle());
        activity.setCategories(nextActivity.getCategories());
        activity.setAttributes(nextActivity.getAttributes());
        return true;
    }
}
