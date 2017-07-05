package com.github.syominsergey.spent_resources_analyzer.sources;

/**
 * Created by Sergey on 29.06.2017.
 */
public class MoneyFormatter implements Formatter<Integer> {

    String moneySign;

    public MoneyFormatter(String moneySign) {
        this.moneySign = moneySign;
    }

    @Override
    public String format(Integer value) {
        StringBuilder sb = new StringBuilder();
        sb.append(value);
        sb.append(moneySign);
        return sb.toString();
    }

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }
}
