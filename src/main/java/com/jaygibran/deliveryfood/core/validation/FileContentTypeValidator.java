package com.jaygibran.deliveryfood.core.validation;

import org.springframework.http.MediaType;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {


    private String[] contentTypeAllowed;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        contentTypeAllowed = constraintAnnotation.allowed();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        return Arrays.stream(contentTypeAllowed).anyMatch(type -> type.equals(file.getContentType()));
    }
}
