package com.github.syominsergey.spent_resources_analyzer.sources;

import java.util.List;

/**
 * Created by Sergey on 28.06.2017.
 */
public interface Activity {
    void setTitle(String title);
    void setAttributes(List<Attribute<?>> attributes);
    void setCategories(List<List<String>> categories);
}
