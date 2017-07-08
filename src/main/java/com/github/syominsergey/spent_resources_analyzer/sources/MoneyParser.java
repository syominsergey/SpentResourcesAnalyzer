package com.github.syominsergey.spent_resources_analyzer.sources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sergey on 28.06.2017.
 */
public class MoneyParser implements Parser<Integer> {

    String moneySign;

    public MoneyParser(String moneySign) {
        this.moneySign = moneySign;
    }

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public Integer parse(String s, boolean strict) {
        if(s.endsWith(moneySign)){
            s = s.substring(0, s.length() - moneySign.length());
        } else {
            String msg = String.format(
                    "Текст '%s', описывающий денежную сумму, не оканчивается на знак валюты '%s'",
                    s,
                    moneySign
            );
            if(strict){
                throw new RuntimeException(msg);
            } else {
                LOG.warn(msg);
            }
        }
        int value = Integer.parseInt(s);
        return value;
    }

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }
}
