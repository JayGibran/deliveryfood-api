package com.jaygibran.deliveryfood.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ValueZeroIncludeDescriptionValidator.class)
public @interface ValueZeroIncludeDescription {
    String valueField();

    String descriptionField();

    String mandatoryDescription();

    String message() default "mandatory description is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

