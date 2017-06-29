package com.github.syominsergey.spent_resources_analyzer.sources;

/**
 * Created by Sergey on 29.06.2017.
 */
public class TimeAttributeType extends IntType implements AttributeType<Integer> {

    String name;
    String hoursSign;
    String minutesSign;

    public TimeAttributeType(String name, String hoursSign, String minutesSign) {
        this.name = name;
        this.hoursSign = hoursSign;
        this.minutesSign = minutesSign;
    }

    public TimeAttributeType() {
        this("время", "ч", "м");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Parser<Integer> createParser() {
        return new TimeParser(hoursSign, minutesSign);
    }

    @Override
    public Formatter<Integer> createFormatter() {
        return new TimeFormatter(hoursSign, minutesSign);
    }

    @Override
    public String toString() {
        return "TimeAttributeType{" +
                "name='" + name + '\'' +
                '}';
    }
}
