package com.example.ToriApi;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StringListConverter.class);
    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        try {
            if (attribute == null || attribute.isEmpty()) {
                return null;
            }
            return String.join(DELIMITER, attribute);

        } catch (Exception e) {
            LOGGER.error(("Ошибка при преобразовании списка строк в базу данных: {}"), e.getMessage());
            return null;
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null || dbData.isEmpty()) {
                return new ArrayList<>();
            }
            return new ArrayList<>(Arrays.asList(dbData.split(DELIMITER)));
        } catch (Exception e) {
            LOGGER.error("Ошибка при преобразовании строки из базы данных в список строк: {}", e.getMessage());
            return new ArrayList<>();
        }
    }
}
