package com.github.syominsergey.spent_resources_analyzer.model;

import com.github.syominsergey.spent_resources_analyzer.sources.Acc;
import com.github.syominsergey.spent_resources_analyzer.sources.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sergey on 26.07.2017.
 */
public class AttributeSummator implements ActivityInspector {

    protected String attributeName;
    protected Acc<?> attributeAcc;

    public AttributeSummator(String attributeName, Acc<?> attributeAcc) {
        this.attributeName = attributeName;
        this.attributeAcc = attributeAcc;
    }

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public void inspectNextActivity(Activity activity) {
        Attribute<?> attribute = activity.attributes.get(attributeName);
        if(attribute == null){
            return;
        }
        if(!attributeAcc.getType().isAssignableFrom(attribute.getMeta().getType())){
            LOG.warn(
                    "В действии {} атрибут с именем {} имеет тип значения {}, но пришедший аккумулятор для этого имени" +
                            " имеет тип значения {}, к которому тип атрибута не может быть приведён",
                    activity.name,
                    attribute.getMeta().getName(),
                    attribute.getMeta().getType(),
                    attributeAcc.getType()
            );
            return;
        }
        ((Acc)attributeAcc).add(attribute.getValue());
    }
}
