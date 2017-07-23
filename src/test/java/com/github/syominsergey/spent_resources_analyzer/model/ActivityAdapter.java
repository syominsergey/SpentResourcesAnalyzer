package com.github.syominsergey.spent_resources_analyzer.model;

import com.github.syominsergey.spent_resources_analyzer.sources.ActivityImpl;
import com.github.syominsergey.spent_resources_analyzer.sources.Attribute;

import java.util.*;

/**
 * Created by Sergey on 20.07.2017.
 */
public class ActivityAdapter {
    String title;
    Set<Attribute<?>> attributes;
    boolean isExpected;

    public ActivityAdapter(ActivityImpl activity, boolean isExpected) {
        this.title = activity.getTitle();
        this.attributes = new HashSet<>(activity.getAttributes());
        this.isExpected = isExpected;
    }

    public ActivityAdapter(Activity activity, boolean isExpected) {
        this.title = activity.name;
        this.attributes = new HashSet<>(activity.attributes.values());
        this.isExpected = isExpected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivityAdapter that = (ActivityAdapter) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return attributes != null ? attributes.equals(that.attributes) : that.attributes == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (attributes != null ? attributes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ActivityAdapter{" +
                "title='" + title + '\'' +
                ", attributes=" + attributes +
                ", isExpected=" + isExpected +
                '}';
    }
}
