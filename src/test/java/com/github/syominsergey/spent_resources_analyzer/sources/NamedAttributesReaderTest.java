package com.github.syominsergey.spent_resources_analyzer.sources;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sergey on 06.07.2017.
 */
public class NamedAttributesReaderTest {

    private MoneyAttributeMeta moneyAttributeMeta = new MoneyAttributeMeta();
    private final TimeAttributeMeta timeAttributeMeta = new TimeAttributeMeta();

    protected class ReadAttributesChecker extends ReadAttributesCheckerAbstract {

        public ReadAttributesChecker(
                String sourceString,
                List<Attribute<?>> attributesExpected,
                AttributesReader attributesReader
        ) {
            super(sourceString, attributesExpected, attributesReader);
        }

        public ReadAttributesChecker(String sourceString) {
            super(sourceString);
        }

        @Override
        protected AttributesReader createDefaultAttributeReader() {
            List<AttributeMeta<?>> attributeMetas = new ArrayList<>();
            attributeMetas.add(moneyAttributeMeta);
            attributeMetas.add(timeAttributeMeta);
            AttributesReader attributesReader = new NamedAttributesReader(
                    attributeMetas, ", ", Arrays.asList(": ", " ")
            );
            return attributesReader;
        }
    }

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Test
    public void readAttributes1() throws Exception {
        LOG.debug("starting test");
        ReadAttributesCheckerAbstract checker = new ReadAttributesChecker("стоимость: 153р, время: 1ч10м");
        checker.addExpectedAttribute(153, moneyAttributeMeta);
        checker.addExpectedAttribute(70, timeAttributeMeta);
        checker.check();
    }

    @Test
    public void readAttributes2() throws Exception {
        LOG.debug("starting test");
        ReadAttributesCheckerAbstract checker = new ReadAttributesChecker("стоимость: 200р");
        checker.addExpectedAttribute(200, moneyAttributeMeta);
        checker.check();
    }

    @Test
    public void readAttributes3() throws Exception {
        LOG.debug("starting test");
        ReadAttributesCheckerAbstract checker = new ReadAttributesChecker("время: 5ч");
        checker.addExpectedAttribute(300, timeAttributeMeta);
        checker.check();
    }

    @Test
    public void readAttributes4() throws Exception {
        LOG.debug("starting test");
        ReadAttributesCheckerAbstract checker = new ReadAttributesChecker("стоимость: 234, время: 12");
        checker.addExpectedAttribute(234, moneyAttributeMeta);
        checker.addExpectedAttribute(12, timeAttributeMeta);
        checker.check();
    }

    @Test
    public void readAttributes5() throws Exception {
        LOG.debug("starting test");
        ReadAttributesCheckerAbstract checker = new ReadAttributesChecker("стоимость: 100");
        checker.addExpectedAttribute(100, moneyAttributeMeta);
        checker.check();
    }

    @Test
    public void readAttributes6() throws Exception {
        LOG.debug("starting test");
        ReadAttributesCheckerAbstract checker = new ReadAttributesChecker("время: 23");
        checker.addExpectedAttribute(23, timeAttributeMeta);
        checker.check();
    }

    @Test
    public void readAttributes7() throws Exception {
        LOG.debug("starting test");
        ReadAttributesCheckerAbstract checker = new ReadAttributesChecker("153р, 1ч10м");
        checker.check();
    }

    @Test
    public void readAttributes8() throws Exception {
        LOG.debug("starting test");
        ReadAttributesCheckerAbstract checker = new ReadAttributesChecker("288р");
        checker.check();
    }

    @Test
    public void readAttributes9() throws Exception {
        LOG.debug("starting test");
        ReadAttributesCheckerAbstract checker = new ReadAttributesChecker("3ч");
        checker.check();
    }

}