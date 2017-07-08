package com.github.syominsergey.spent_resources_analyzer.sources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sergey on 29.06.2017.
 */
public class TimeParser implements Parser<Integer> {

    String hoursSign;
    String minutesSign;

    public TimeParser(String hoursSign, String minutesSign) {
        this.hoursSign = hoursSign;
        this.minutesSign = minutesSign;
    }

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public Integer parse(String s, boolean strict) {
        int totalMinutes = 0;
        int startPos = 0;
        int nextSignPos = s.indexOf(hoursSign, startPos);
        boolean hoursDefined;
        if(nextSignPos != -1){
            String hoursSubstring = s.substring(0, nextSignPos);
            int hours = Integer.parseInt(hoursSubstring);
            totalMinutes += hours * 60;
            startPos = nextSignPos + hoursSign.length();
            hoursDefined = true;
        } else {
            hoursDefined = false;
        }
        if (startPos < s.length()) {
            nextSignPos = s.indexOf(minutesSign, startPos);
            String minutesSubstring;
            if(nextSignPos != -1){
                minutesSubstring = s.substring(startPos, nextSignPos);
            } else {
                minutesSubstring = s.substring(startPos, s.length());
                String msg = String.format(
                        "Строка '%s' содержит знаки '%s', относящиеся к минутам, но не содержит в конце самого знака минут '%s'",
                        s,
                        minutesSubstring,
                        minutesSign
                );
                if(strict && !hoursDefined){
                    throw new RuntimeException(msg);
                } else {
                    LOG.warn(msg);
                }
            }
            minutesSubstring = minutesSubstring.trim();
            int minutes = Integer.parseInt(minutesSubstring);
            totalMinutes += minutes;
        }
        return totalMinutes;
    }

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }
}
