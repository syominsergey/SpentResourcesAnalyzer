package com.github.syominsergey.spent_resources_analyzer.sources;

import java.util.Comparator;

/**
 * Created by Sergey on 28.06.2017.
 */
public interface AttributeMeta<T> {
    String getName();
    Class<T> getType();
    Acc<T> createAcc();
    ElementaryParser<T> createParser();
    Formatter<T> createFormatter();
    Comparator<T> createComparator();
    boolean isZero(T value);
}
