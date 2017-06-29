package com.github.syominsergey.spent_resources_analyzer.sources;

/**
 * Created by Sergey on 29.06.2017.
 */
public class MoneyAttributeType extends IntType implements AttributeType<Integer> {

    String name;
    String moneySign;

    public MoneyAttributeType(String name, String moneySign) {
        this.name = name;
        this.moneySign = moneySign;
    }

    public MoneyAttributeType() {
        this("стоимость", "р");
    }

    @Override
    public String getName() {
        return name;
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
        return "MoneyAttributeType{" +
                "name='" + name + '\'' +
                '}';
    }
}
