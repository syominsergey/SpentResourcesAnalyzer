package com.github.syominsergey.spent_resources_analyzer.sources;

/**
 * Created by Sergey on 29.06.2017.
 */
public class TimeFormatter implements Formatter<Integer> {

    String hoursSign;
    String minutesSign;

    public TimeFormatter(String hoursSign, String minutesSign) {
        this.hoursSign = hoursSign;
        this.minutesSign = minutesSign;
    }

    @Override
    public String format(Integer value) {
        int hours = value / 60;
        int minutes = value % 60;
        StringBuilder sb = new StringBuilder();
        if(hours > 0){
            sb.append(hours);
            sb.append(hoursSign);
        }
        if(minutes > 0){
            sb.append(minutes);
            sb.append(minutesSign);
        }
        return sb.toString();
    }

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }
}
