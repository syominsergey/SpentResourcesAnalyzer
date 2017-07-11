package com.github.syominsergey.spent_resources_analyzer.sources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Sergey on 09.07.2017.
 */
public class AttributesReaderImpl implements AttributesReader {

    String attributesSep;
    List<AttributeParser<?>> attributeParsers;

    public AttributesReaderImpl(
            List<AttributeParser<?>> attributeParsers,
            String attributesSep
    ) {
        this.attributeParsers = attributeParsers;
        this.attributesSep = attributesSep;
    }

    public AttributesReaderImpl(
            AttributeParser<?> attributeParser,
            String attributesSep
    ) {
        this(Collections.singletonList(attributeParser), attributesSep);
    }

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    protected <AttributeId> Map<AttributeId, Attribute<?>> readAttributesWithSpecialParser(
            String sourceString,
            String[] attributeStrings,
            AttributeParser<AttributeId> attributeParser
    ){
        MarkedAttributeImpl<AttributeId> markedAttribute = new MarkedAttributeImpl<AttributeId>();
        Map<AttributeId, Attribute<?>> attributes = new HashMap<>();
        Set<AttributeId> rollingAttributeIds = new HashSet<>();
        for (int i = 0; i < attributeStrings.length; i++) {
            String attributeString = attributeStrings[i];
            try {
                attributeParser.parse(attributeString, markedAttribute);
            } catch (Exception e) {
                String msg = String.format(
                        "Ошибка при разборе атрибута '%s' под номером %d из строки атрибутов '%s'",
                        attributeString,
                        i,
                        sourceString
                );
                LOG.warn(msg, e);
                continue;
            }
            AttributeId attributeId = markedAttribute.getAttributeId();
            if (rollingAttributeIds.contains(attributeId)) {
                LOG.warn(
                        "Атрибут '{}' под номером {} в строке атрибутов '{}' получил идентификатор '{}'," +
                                " который содержится в списке повторяющихся. Пропускаем.",
                        attributeString, i, sourceString, attributeId
                );
                continue;
            }
            Attribute<?> previousAttribute = attributes.put(attributeId, markedAttribute.getAttribute());
            if (previousAttribute == null) {
                continue;
            }
            String msg = String.format(
                    "Для атрибута '%s' под номером %d в строке атрибутов '%s' получен идентификатор" +
                            " '%s', который уже ранее применялся для атрибута '%s' в данной строке атрибутов," +
                            " что ведёт к неоднозначности. Атрибуты по данному идентификатору не будут учтены",
                    attributeString,
                    i,
                    sourceString,
                    attributeId,
                    previousAttribute
            );
            LOG.warn(msg);
            attributes.remove(attributeId);
            rollingAttributeIds.add(attributeId);
        }
        return attributes;
    }

    @Override
    public void readAttributes(String sourceString, List<Attribute<?>> attributesOut) {
        attributesOut.clear();
        String[] attributeStrings = sourceString.split(attributesSep);
        for (AttributeParser<?> attributeParser : attributeParsers) {
            Map<?, Attribute<?>> attributes = readAttributesWithSpecialParser(sourceString, attributeStrings, attributeParser);
            if(!attributes.isEmpty()){
                for (Attribute<?> attribute : attributes.values()) {
                    attributesOut.add(attribute);
                }
                break;
            }
        }
    }
}
