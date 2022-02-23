package com.jaygibran.deliveryfood.domain.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

public class ObjectMerger<T> {

    private final ObjectMapper objectMapper;
    private final Class<T> type;

    public ObjectMerger(Class<T> type) {
        this.objectMapper = new ObjectMapper();
        this.type = type;
    }

    public void merge(Map<String, Object> originData, T objectToUpdate) {
        T objectOrigen = objectMapper.convertValue(originData, type);

        originData.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(type, key);
            if (field != null) {
                field.setAccessible(true);

                Object newValue = ReflectionUtils.getField(field, objectOrigen);

                ReflectionUtils.setField(field, objectToUpdate, newValue);
            }
        });
    }
}
