package com.github.syominsergey.spent_resources_analyzer.model;

import com.github.syominsergey.spent_resources_analyzer.sources.Acc;
import com.github.syominsergey.spent_resources_analyzer.sources.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergey on 04.07.2017.
 */
class Activity {

    String name;

    Map<String, Attribute<?>> attributes;

    List<Category> categories;

    public Activity(String name, List<Attribute<?>> attributes) {
        this.name = name;
        this.attributes = new HashMap<>(attributes.size());
        for (Attribute<?> attribute : attributes) {
            this.attributes.put(attribute.getMeta().getName(), attribute);
        }
        this.categories = new ArrayList<>();
    }

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public String toString() {
        return "Activity{" +
                "name='" + name + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
