package com.github.syominsergey.spent_resources_analyzer.sources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergey on 11.07.2017.
 */
public class TypedAttributesParser implements AttributeParser<ElementaryParser<?>> {

    public TypedAttributesParser(
            List<AttributeMeta<?>> attributeMetas
    ) {
        for (AttributeMeta<?> attributeMeta : attributeMetas) {
            ElementaryParser<?> parser = attributeMeta.createParser();
            AttributeMeta<?> previousMeta = parsers.put(parser, attributeMeta);
            if(previousMeta != null){
                String s = String.format(
                        "Для построения парсера безымянных атрибутов получен список описаний атрибутов '%s'," +
                                "в котором дублируется парсер '%s'",
                        attributeMetas,
                        parser
                );
                throw new IllegalArgumentException(s);
            }
        }
    }

    protected Map<ElementaryParser<?>, AttributeMeta<?>> parsers = new HashMap<>();

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public void parse(String attributeString, MarkedAttribute<ElementaryParser<?>> markedAttribute) {
        Map<ElementaryParser<?>, Object> parserResults = new HashMap<>();
        Map<ElementaryParser<?>, Throwable> parserErrors = new HashMap<>();
        for (ElementaryParser<?> parser : parsers.keySet()) {
            Object value;
            try {
                value = parser.parse(attributeString, true);
                parserResults.put(parser, value);
            } catch (Throwable e){
                parserErrors.put(parser, e);
            }
        }
        switch (parserResults.size()) {
            case 0: {
                String msg = String.format(
                        "При разборе строки '%s' не подошёл ни один из парсеров, все" +
                                " вернули исключения: %s",
                        attributeString,
                        parserErrors
                );
                throw new RuntimeException(msg);
            }
            case 1: {
                Map.Entry<ElementaryParser<?>, Object> entry = parserResults.entrySet().iterator().next();
                Attribute attribute = new AttributeImpl(entry.getValue(), parsers.get(entry.getKey()));
                markedAttribute.setAttribute(attribute);
                markedAttribute.setAttributeId(entry.getKey());
            }
            default:{
                String msg = String.format(
                        "При разборе строки '%s' с помощью заданного набора парсеров возникла неоднозначность," +
                                " подошло %d парсрара(ов): %s",
                        attributeString,
                        parserResults.size(),
                        parserResults
                );
                throw new RuntimeException(msg);
            }
        }
    }
}
