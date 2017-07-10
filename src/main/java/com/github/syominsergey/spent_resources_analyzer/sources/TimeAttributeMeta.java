package com.github.syominsergey.spent_resources_analyzer.sources;

/**
 * Created by Sergey on 29.06.2017.
 */
public class TimeAttributeMeta implements AttributeMeta<Integer> {

    String name;
    String hoursSign;
    String minutesSign;

    public TimeAttributeMeta(String name, String hoursSign, String minutesSign) {
        this.name = name;
        this.hoursSign = hoursSign;
        this.minutesSign = minutesSign;
    }

    public TimeAttributeMeta() {
        this("время", "ч", "м");
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
        return new TimeParser(hoursSign, minutesSign);
    }

    @Override
    public Formatter<Integer> createFormatter() {
        return new TimeFormatter(hoursSign, minutesSign);
    }

    @Override
    public String toString() {
        return "TimeAttributeMeta{" +
                "name='" + name + '\'' +
                '}';
    }
}
