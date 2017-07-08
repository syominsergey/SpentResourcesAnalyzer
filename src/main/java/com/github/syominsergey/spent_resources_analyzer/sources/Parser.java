package com.github.syominsergey.spent_resources_analyzer.sources;

/**
 * Created by Sergey on 28.06.2017.
 */
public interface Parser<T> {
    T parse(String s, boolean strict);
    Class<T> getType();
}
