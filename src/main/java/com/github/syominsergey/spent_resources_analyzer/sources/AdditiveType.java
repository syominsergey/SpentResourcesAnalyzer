package com.github.syominsergey.spent_resources_analyzer.sources;

/**
 * Created by Sergey on 28.06.2017.
 */
public interface AdditiveType<T> {
    Acc<T> createAcc();
    Class<T> getType();
}
