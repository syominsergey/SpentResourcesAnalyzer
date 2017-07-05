package com.github.syominsergey.spent_resources_analyzer.model;

import com.github.syominsergey.spent_resources_analyzer.sources.Acc;

/**
 * Created by Sergey on 04.07.2017.
 */
public interface AttributeAggregator {
    void aggregate(String attributeName, Acc<?> attributeAcc);
}
