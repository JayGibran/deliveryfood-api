package com.jaygibran.deliveryfood.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class MultipleValidator implements ConstraintValidator<Multiple, Number> {

    private int multipleNumber;

    @Override
    public void initialize(Multiple constraintAnnotation) {
        this.multipleNumber = constraintAnnotation.number();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        if (value != null) {
            var decimalValue = BigDecimal.valueOf(value.doubleValue());
            var decimalMultiple = BigDecimal.valueOf(this.multipleNumber);
            var remainder = decimalValue.remainder(decimalMultiple);
            return BigDecimal.ZERO.compareTo(remainder) == 0;
        }
        return true;
    }
}
