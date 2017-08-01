package com.github.syominsergey.spent_resources_analyzer.cli;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Sergey on 01.08.2017.
 */
public class Pair<A, B> {
    public A a;
    public B b;

    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public Pair() {
    }

    public static <A, B> void sortListByA(List<Pair<A, B>> list, Comparator<A> comparator){
        list.sort((o1, o2) -> comparator.compare(o1.a, o2.a));
    }

    public static <A, B> void sortListByB(List<Pair<A, B>> list, Comparator<B> comparator){
        list.sort((o1, o2) -> comparator.compare(o1.b, o2.b));
    }

}
