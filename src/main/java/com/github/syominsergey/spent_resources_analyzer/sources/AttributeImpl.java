package com.github.syominsergey.spent_resources_analyzer.sources;

/**
 * Created by Sergey on 28.06.2017.
 */
public class AttributeImpl<T> implements Attribute<T> {

    protected T value;
    protected AttributeMeta<T> meta;

    public AttributeImpl(T value, AttributeMeta<T> meta) {
        this.value = value;
        this.meta = meta;
    }

    public T getValue() {
        return value;
    }

    public AttributeMeta<T> getMeta() {
        return meta;
    }

    @Override
    public String toString() {
        return "AttributeImpl{" +
                "value=" + value +
                ", meta=" + meta +
                '}';
    }
}
