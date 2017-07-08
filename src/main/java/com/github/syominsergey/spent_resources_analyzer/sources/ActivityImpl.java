package com.github.syominsergey.spent_resources_analyzer.sources;

import java.util.List;

/**
 * Created by Sergey on 05.07.2017.
 */
class ActivityImpl implements Activity {

    String title;

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    List<Attribute<?>> attributes;

    @Override
    public void setAttributes(List<Attribute<?>> attributes) {
        this.attributes = attributes;
    }

    public List<Attribute<?>> getAttributes() {
        return attributes;
    }

    List<List<String>> categories;

    @Override
    public void setCategories(List<List<String>> categories) {
        this.categories = categories;
    }

    public List<List<String>> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "ActivityImpl{\n" +
                "\ttitle='" + title + "\'\n" +
                "\tattributes=" + attributes + "\n" +
                "\tcategories=" + categories + "\n" +
                '}';
    }
}
