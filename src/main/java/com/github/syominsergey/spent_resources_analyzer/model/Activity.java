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
class Activity implements AttributeAggregator {

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
    public void aggregate(String attributeName, Acc<?> attributeAcc){
        Attribute<?> attribute = attributes.get(attributeName);
        if(attribute == null){
            return;
        }
        if(!attributeAcc.getType().isAssignableFrom(attribute.getMeta().getType())){
            LOG.warn(
                    "В действии {} атрибут с именем {} имеет тип значения {}, но пришедший аккумулятор для этого имени" +
                            " имеет тип значения {}, к которому тип атрибута не может быть приведён",
                    name,
                    attribute.getMeta().getName(),
                    attribute.getMeta().getType(),
                    attributeAcc.getType()
            );
            return;
        }
        ((Acc)attributeAcc).add(attribute.getValue());
    }

    @Override
    public String toString() {
        return "Activity{" +
                "name='" + name + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
