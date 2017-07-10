package com.github.syominsergey.spent_resources_analyzer.sources;

/**
 * Created by Sergey on 11.07.2017.
 */
public class MarkedAttributeImpl<AttributeId> implements MarkedAttribute<AttributeId> {

    AttributeId attributeId;
    Attribute<?> attribute;

    public AttributeId getAttributeId() {
        return attributeId;
    }

    public Attribute<?> getAttribute() {
        return attribute;
    }

    @Override
    public void setAttributeId(AttributeId attributeId) {
        this.attributeId = attributeId;
    }

    @Override
    public void setAttribute(Attribute<?> attribute) {
        this.attribute = attribute;
    }
}
