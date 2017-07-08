package com.github.syominsergey.spent_resources_analyzer.sources;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sergey on 08.07.2017.
 */
public abstract class ReadAttributesCheckerAbstract {
    String sourceString;
    List<Attribute<?>> attributesExpected;
    AttributesReader attributesReader;

    public ReadAttributesCheckerAbstract(
            String sourceString,
            List<Attribute<?>> attributesExpected,
            AttributesReader attributesReader
    ) {
        this.sourceString = sourceString;
        this.attributesExpected = attributesExpected;
        this.attributesReader = attributesReader;
    }

    public ReadAttributesCheckerAbstract(
            String sourceString
    ) {
        this(sourceString, new ArrayList<>(), null);
    }

    public <T> void addExpectedAttribute(T value, AttributeMeta<T> meta) {
        attributesExpected.add(new AttributeImpl<>(value, meta));
    }

    public void setAttributesReader(AttributesReader attributesReader) {
        this.attributesReader = attributesReader;
    }

    public void setAttributesExpected(List<Attribute<?>> attributesExpected) {
        this.attributesExpected = attributesExpected;
    }

    abstract protected AttributesReader createDefaultAttributeReader();

    public void check() throws Exception {
        if (attributesExpected == null) {
            throw new IllegalStateException("Ожидаемые атрибуты не были заданы!");
        }
        if (attributesReader == null) {
            attributesReader = createDefaultAttributeReader();
        }
        List<Attribute<?>> attributesActual = new ArrayList<>();
        attributesReader.readAttributes(sourceString, attributesActual);
        assertEquals(
                "Количество ожидаемых и фактических атрибутов дб одинаковым",
                attributesExpected.size(),
                attributesActual.size()
        );
        for (int i = 0; i < attributesExpected.size(); i++) {
            Attribute<?> attributeExpected = attributesExpected.get(i);
            Attribute<?> attributeActual = attributesActual.get(i);
            assertEquals(
                    "Значения атрибутов должны совпадать",
                    attributeExpected.getValue(),
                    attributeActual.getValue()
            );
            assertEquals(
                    "Названия атрибутов должны совпадать",
                    attributeExpected.getMeta().getName(),
                    attributeExpected.getMeta().getName()
            );
            assertEquals(
                    "Классы атрибутов должны совпадать",
                    attributeExpected.getMeta().getType(),
                    attributeActual.getMeta().getType()
            );
        }
    }
}
