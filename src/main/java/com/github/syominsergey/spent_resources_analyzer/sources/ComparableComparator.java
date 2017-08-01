package com.github.syominsergey.spent_resources_analyzer.sources;

import java.util.Comparator;

/**
 * Created by Sergey on 01.08.2017.
 */
public class ComparableComparator<T extends Comparable<T>> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
        return o1.compareTo(o2);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
