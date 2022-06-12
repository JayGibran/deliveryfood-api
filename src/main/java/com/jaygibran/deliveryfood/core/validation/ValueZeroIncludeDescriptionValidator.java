package com.jaygibran.deliveryfood.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

public class ValueZeroIncludeDescriptionValidator implements ConstraintValidator<ValueZeroIncludeDescription, Object> {

    private String valueField;
    private String descriptionField;
    private String mandatoryDescription;

    @Override
    public void initialize(ValueZeroIncludeDescription constraint) {
        this.valueField = constraint.valueField();
        this.descriptionField = constraint.descriptionField();
        this.mandatoryDescription = constraint.mandatoryDescription();
    }

    @Override
    public boolean isValid(Object validationObject, ConstraintValidatorContext context) {
        try {
            BigDecimal value = (BigDecimal) BeanUtils.getPropertyDescriptor(validationObject.getClass(), valueField)
                    .getReadMethod().invoke(validationObject);

            String description = (String) BeanUtils.getPropertyDescriptor(validationObject.getClass(), descriptionField)
                    .getReadMethod().invoke(validationObject);
            if (value != null && BigDecimal.ZERO.compareTo(value) == 0 && description != null) {
                return description.toLowerCase().contains(mandatoryDescription.toLowerCase());
            }
        } catch (Exception e) {
            throw new ValidationException(e);
        }

        return true;
    }
}
