package com.jaygibran.deliveryfood.domain.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Map;

public class ObjectMerger<T> {

    private final ObjectMapper objectMapper;
    private final Class<T> type;

    public ObjectMerger(Class<T> type) {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        this.type = type;
    }

    public void merge(Map<String, Object> originData, T objectToUpdate, HttpServletRequest request) {
        try {
            T objectOrigen = objectMapper.convertValue(originData, type);

            originData.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(type, key);
                if (field != null) {
                    field.setAccessible(true);

                    Object newValue = ReflectionUtils.getField(field, objectOrigen);

                    ReflectionUtils.setField(field, objectToUpdate, newValue);
                }
            });
        } catch (IllegalArgumentException e) {
            ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(request);
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, servletServerHttpRequest);
        }
    }
}
