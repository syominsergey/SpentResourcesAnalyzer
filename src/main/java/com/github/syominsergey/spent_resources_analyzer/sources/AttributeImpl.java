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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttributeImpl<?> attribute = (AttributeImpl<?>) o;

        if (value != null ? !value.equals(attribute.value) : attribute.value != null) return false;
        return meta != null ? meta.equals(attribute.meta) : attribute.meta == null;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (meta != null ? meta.hashCode() : 0);
        return result;
    }
}
