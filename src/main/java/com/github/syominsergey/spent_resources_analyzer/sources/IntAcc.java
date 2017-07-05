package com.github.syominsergey.spent_resources_analyzer.sources;

/**
 * Created by Sergey on 05.07.2017.
 */
public class IntAcc implements Acc<Integer> {

    protected int sum = 0;

    @Override
    public void add(Integer val) {
        sum += val;
    }

    @Override
    public Integer getSum() {
        return sum;
    }

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }
}
