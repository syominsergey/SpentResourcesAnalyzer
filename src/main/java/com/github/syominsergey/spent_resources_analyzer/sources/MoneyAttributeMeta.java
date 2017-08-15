package com.github.syominsergey.spent_resources_analyzer.sources;

import java.util.Comparator;

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
    public ElementaryParser<Integer> createParser() {
        return new MoneyParser(moneySign);
    }

    @Override
    public Formatter<Integer> createFormatter() {
        return new MoneyFormatter(moneySign);
    }

    @Override
    public Comparator<Integer> createComparator() {
        return new ComparableComparator<>();
    }

    private static final Integer ZERO = 0;

    @Override
    public boolean isZero(Integer value) {
        return ZERO.equals(value);
    }

    @Override
    public String toString() {
        return "MoneyAttributeMeta{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoneyAttributeMeta that = (MoneyAttributeMeta) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return moneySign != null ? moneySign.equals(that.moneySign) : that.moneySign == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (moneySign != null ? moneySign.hashCode() : 0);
        return result;
    }
}
