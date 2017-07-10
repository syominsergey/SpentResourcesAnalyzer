package com.github.syominsergey.spent_resources_analyzer.sources;

/**
 * Created by Sergey on 09.07.2017.
 */
public interface AttributeParser<AttributeId> {
    void parse(String s, MarkedAttribute<AttributeId> markedAttribute);
}
