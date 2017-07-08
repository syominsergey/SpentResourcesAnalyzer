package com.github.syominsergey.spent_resources_analyzer.sources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergey on 06.07.2017.
 */
public class NamedAttributesReader implements AttributesReader {

    protected String attributesSep;
    protected List<String> attributeNameValueSeparators;

    public NamedAttributesReader(
            List<AttributeMeta<?>> attributeMetas,
            String attributesSep,
            String attributeNameValueSeparator
    ){
        this(attributeMetas, attributesSep, Collections.singletonList(attributeNameValueSeparator));
    }

    public NamedAttributesReader(
            List<AttributeMeta<?>> attributeMetas,
            String attributesSep,
            List<String> attributeNameValueSeparators
    ) {
        this.attributesSep = attributesSep;
        if(attributeNameValueSeparators == null){
            throw new NullPointerException("Параметр attributeNameValueSeparators не должен быть null!");
        }
        if (attributeNameValueSeparators.isEmpty()) {
            throw new IllegalArgumentException("Список attributeNameValueSeparators должен содержать хотя бы 1 элемент, но он пуст!");
        }
        this.attributeNameValueSeparators = attributeNameValueSeparators;
        for (AttributeMeta<?> attributeMeta : attributeMetas) {
            AttributeContext<?> previousContext = attributeContexts.put(
                    attributeMeta.getName(),
                    AttributeContext.create(attributeMeta)
            );
            if (previousContext != null) {
                String s = String.format(
                        "Для построения парсера именованных атрибутов получен список описаний атрибутов," +
                                "в котором дублируется имя '%s'",
                        attributeMeta.getName()
                );
                throw new IllegalArgumentException(s);
            }
        }
    }

    protected Map<String, AttributeContext<?>> attributeContexts = new HashMap<String, AttributeContext<?>>();

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public void readAttributes(String sourceString, List<Attribute<?>> attributesOut) {
        attributesOut.clear();
        String[] attributeStrings = sourceString.split(attributesSep);
        for (int i = 0; i < attributeStrings.length; i++) {
            String attributeString = attributeStrings[i];
            String[] nameValue = null;
            for (String attributeNameValueSeparator : attributeNameValueSeparators) {
                nameValue = attributeString.split(attributeNameValueSeparator);
                if(nameValue.length == 2){
                    break;
                }
            }
            if(nameValue.length != 2){
                LOG.warn(
                        "При разборе исходной строки '{}' обнаружена подстрока атрибута '{}', которая не разделяется с помощью " +
                                "сепараторов '{}' на имя и значение, т.к. в результате имеем кол-во элементов, отличное от 2",
                        sourceString,
                        attributeString,
                        attributeNameValueSeparators,
                        nameValue.length
                );
                continue;
            }
            String attributeName = nameValue[0];
            String attributeValueString = nameValue[1];
            AttributeContext<?> attributeContext = attributeContexts.get(attributeName);
            if(attributeContext == null){
                LOG.warn(
                        "При разборе исходной строки '{}' обнаружена строка атрибута '{}', которая с помощью" +
                                " сепаратора '{}' даёт имя '{}', которого нет в списке имён атрибутов '{}'",
                        sourceString,
                        attributeString,
                        attributesSep,
                        attributeName,
                        attributeContexts.keySet()
                );
                continue;
            }
            Attribute<?> attribute;
            try {
                attribute = attributeContext.parse(attributeValueString);
            } catch (Throwable e) {
                String s = String.format(
                        "Ошибка при разборе значения '%s' атрибута с именем '%s'",
                        attributeValueString,
                        attributeName
                );
                LOG.warn(s, e);
                continue;
            }
            attributesOut.add(attribute);
        }
    }

    private static class AttributeContext<T> {
        public AttributeMeta<T> attributeMeta;
        public Parser<T> parser;

        public static <T> AttributeContext<T> create(AttributeMeta<T> attributeMeta){
            return new AttributeContext<T>(attributeMeta, attributeMeta.createParser());
        }

        public AttributeContext(AttributeMeta<T> attributeMeta, Parser<T> parser) {
            this.attributeMeta = attributeMeta;
            this.parser = parser;
        }

        public Attribute<T> parse(String s){
            T value = parser.parse(s, false);
            return new AttributeImpl<T>(value, attributeMeta);
        }
    }
}
