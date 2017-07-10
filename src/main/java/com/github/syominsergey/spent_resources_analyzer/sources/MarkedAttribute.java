package com.github.syominsergey.spent_resources_analyzer.sources;

/**
 * Created by Sergey on 11.07.2017.
 */
public interface MarkedAttribute<AttributeId> {
    void setAttributeId(AttributeId attributeId);
    void setAttribute(Attribute<?> attribute);
}
