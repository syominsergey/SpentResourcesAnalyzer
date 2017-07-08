package com.github.syominsergey.spent_resources_analyzer.sources;

import java.util.List;

/**
 * Created by Sergey on 06.07.2017.
 */
public interface AttributesReader {
    void readAttributes(String sourceString, List<Attribute<?>> attributesOut);
}
