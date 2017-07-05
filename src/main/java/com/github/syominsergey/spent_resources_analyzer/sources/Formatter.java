package com.github.syominsergey.spent_resources_analyzer.sources;

/**
 * Created by Sergey on 28.06.2017.
 */
public interface Formatter<T> {
    String format(T value);
    Class<T> getType();
}
