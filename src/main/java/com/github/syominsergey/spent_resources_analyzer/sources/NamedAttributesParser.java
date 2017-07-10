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
public class NamedAttributesParser implements AttributeParser<String> {

    protected List<String> attributeNameValueSeparators;

    public NamedAttributesParser(
            List<AttributeMeta<?>> attributeMetas,
            String attributeNameValueSeparator
    ){
        this(attributeMetas, Collections.singletonList(attributeNameValueSeparator));
    }

    public NamedAttributesParser(
            List<AttributeMeta<?>> attributeMetas,
            List<String> attributeNameValueSeparators
    ) {
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
    public void parse(String attributeString, MarkedAttribute<String> markedAttribute) {
        String[] nameValue = null;
        for (String attributeNameValueSeparator : attributeNameValueSeparators) {
            nameValue = attributeString.split(attributeNameValueSeparator);
            if(nameValue.length == 2){
                break;
            }
        }
        if(nameValue.length != 2){
            String msg = String.format(
                    "Cтрока атрибута '%s' не разделяется с помощью " +
                            "сепараторов '%s' на имя и значение, т.к. в результате имеем кол-во элементов %d, отличное от 2",
                    attributeString,
                    attributeNameValueSeparators,
                    nameValue.length
            );
            throw new RuntimeException(msg);
        }
        String attributeName = nameValue[0];
        String attributeValueString = nameValue[1];
        AttributeContext<?> attributeContext = attributeContexts.get(attributeName);
        if(attributeContext == null){
            String msg = String.format(
                    "При разборе строка атрибута '%s', " +
                            "даёт имя '%s', которого нет в списке имён атрибутов %s",
                    attributeString,
                    attributeName,
                    attributeContexts.keySet()
            );
            throw new RuntimeException(msg);
        }
        Attribute<?> attribute;
        try {
            attribute = attributeContext.parse(attributeValueString);
        } catch (Throwable e) {
            String msg = String.format(
                    "Ошибка при разборе значения '%s' атрибута с именем '%s'",
                    attributeValueString,
                    attributeName
            );
            throw new RuntimeException(msg, e);
        }
        markedAttribute.setAttributeId(attributeName);
        markedAttribute.setAttribute(attribute);
    }

    private static class AttributeContext<T> {
        public AttributeMeta<T> attributeMeta;
        public ElementaryParser<T> parser;

        public static <T> AttributeContext<T> create(AttributeMeta<T> attributeMeta){
            return new AttributeContext<T>(attributeMeta, attributeMeta.createParser());
        }

        public AttributeContext(AttributeMeta<T> attributeMeta, ElementaryParser<T> parser) {
            this.attributeMeta = attributeMeta;
            this.parser = parser;
        }

        public Attribute<T> parse(String s){
            T value = parser.parse(s, false);
            return new AttributeImpl<T>(value, attributeMeta);
        }
    }
}
