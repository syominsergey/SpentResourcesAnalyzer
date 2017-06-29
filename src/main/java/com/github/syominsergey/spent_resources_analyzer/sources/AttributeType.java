package com.github.syominsergey.spent_resources_analyzer.sources;

/**
 * Created by Sergey on 28.06.2017.
 */
public interface AttributeType<T> extends AdditiveType<T> {
    String getName();
    Parser<T> createParser();
    Formatter<T> createFormatter();
}
