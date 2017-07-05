package com.github.syominsergey.spent_resources_analyzer.sources;

/**
 * Created by Sergey on 29.06.2017.
 */
public class MoneyAttributeMeta implements AttributeMeta<Integer> {

    String name;
    String moneySign;

    public MoneyAttributeMeta(String name, String moneySign) {
        this.name = name;
        this.moneySign = moneySign;
    }

    public MoneyAttributeMeta() {
        this("стоимость", "р");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }

    @Override
    public Acc<Integer> createAcc() {
        return new IntAcc();
    }

    @Override
    public Parser<Integer> createParser() {
        return new MoneyParser(moneySign);
    }

    @Override
    public Formatter<Integer> createFormatter() {
        return new MoneyFormatter(moneySign);
    }

    @Override
    public String toString() {
        return "MoneyAttributeMeta{" +
                "name='" + name + '\'' +
                '}';
    }
}
