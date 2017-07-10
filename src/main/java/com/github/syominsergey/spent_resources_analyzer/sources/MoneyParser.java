package com.github.syominsergey.spent_resources_analyzer.sources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sergey on 28.06.2017.
 */
public class MoneyParser implements ElementaryParser<Integer> {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoneyParser that = (MoneyParser) o;

        return moneySign != null ? moneySign.equals(that.moneySign) : that.moneySign == null;
    }

    @Override
    public int hashCode() {
        return moneySign != null ? moneySign.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "MoneyParser{" +
                "moneySign='" + moneySign + '\'' +
                '}';
    }
}
