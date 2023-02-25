package com.jaygibran.deliveryfood.api.exceptionhandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.jaygibran.deliveryfood.core.validation.ValidationException;
import com.jaygibran.deliveryfood.domain.exception.BusinessException;
import com.jaygibran.deliveryfood.domain.exception.EntityInUseException;
import com.jaygibran.deliveryfood.domain.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String GENERIC_ERROR_MSG = "It happened an internal error. Try again and if error persists, get in touch with the admin.";
    public static final String ACCESS_DENIED_ERROR_MSG = "You don't have the permission to execute this operation.";


    private final MessageSource messageSource;


    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
        }

        String detail = "The body of request is invalid. Check syntax error";
        Problem problem = createApiErrorBuilder(status, ApiErrorType.MESSAGE_NOT_READABLE, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }
        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = String.format("Resource %s, which you tried to access doesn't not exist", ex.getRequestURL());
        Problem problem = createApiErrorBuilder(status, ApiErrorType.RESOURCE_NOT_FOUND, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    @ExceptionHandler(ValidationException.class)
    private ResponseEntity<?> handleValidationException(ValidationException ex, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers,
                                                            HttpStatus status, WebRequest request) {
        String detail = "One or two fields are invalid. Fill in correctly and try again.";

        List<Problem.Object> problemObjects = bindingResult
                .getAllErrors()
                .stream()
                .map(objectError -> {
                            String name = objectError.getObjectName();
                            if (objectError instanceof FieldError) {
                                name = ((FieldError) objectError).getField();
                            }
                            String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                            return Problem.Object.builder()
                                    .name(name)
                                    .userMessage(message)
                                    .build();
                        }
                )
                .collect(Collectors.toList());
        Problem problem = createApiErrorBuilder(status, ApiErrorType.INVALID_DATA, detail)
                .userMessage(detail)
                .objects(problemObjects)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = String
                .format("Url parameter '%s' received the value '%s' which is an invalid type. Fix it and send value consistent with type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
        Problem problem = createApiErrorBuilder(status, ApiErrorType.INVALID_PARAMETER, errorMessage)
                .userMessage(errorMessage)
                .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }


    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());

        String detail = String
                .format("Property '%s' received value '%s', which is a invalid type. Fix and send a valid value with type %s.", path, ex.getValue(), ex.getTargetType().getSimpleName());
        Problem problem = createApiErrorBuilder(status, ApiErrorType.MESSAGE_NOT_READABLE, detail)
                .userMessage(GENERIC_ERROR_MSG)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());

        String detail = String.format("Property '%s' does not exists. Fix or remove it and try again.", path);

        Problem problem = createApiErrorBuilder(status, ApiErrorType.MESSAGE_NOT_READABLE, detail)
                .userMessage(GENERIC_ERROR_MSG)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<?> handleEntityNotFound(EntityNotFoundException ex, WebRequest webRequest) {

        Problem problem = createApiErrorBuilder(HttpStatus.NOT_FOUND, ApiErrorType.RESOURCE_NOT_FOUND, ex.getMessage())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(BusinessException.class)
    private ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest webRequest) {

        Problem problem = createApiErrorBuilder(HttpStatus.BAD_REQUEST, ApiErrorType.BUSINESS_EXCEPTION, ex.getMessage())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(EntityInUseException.class)
    private ResponseEntity<?> handleEntityInUseException(EntityInUseException ex, WebRequest webRequest) {

        Problem problem = createApiErrorBuilder(HttpStatus.CONFLICT, ApiErrorType.ENTITY_IN_USE, ex.getMessage())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.CONFLICT, webRequest);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<Object> handleUncaught(Exception ex, WebRequest webRequest) {
        log.error(ex.getMessage(), ex);

        Problem problem = createApiErrorBuilder(HttpStatus.INTERNAL_SERVER_ERROR, ApiErrorType.SYSTEM_ERROR, GENERIC_ERROR_MSG)
                .userMessage(GENERIC_ERROR_MSG)
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
    }

    @ExceptionHandler(AccessDeniedException.class)
    private ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest webRequest) {
        log.error(ex.getMessage(), ex);
        Problem problem = createApiErrorBuilder(HttpStatus.FORBIDDEN, ApiErrorType.ACCESS_DENIED, ex.getMessage())
                .userMessage(ACCESS_DENIED_ERROR_MSG)
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.FORBIDDEN, webRequest);
    }

    @Override

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null) {
            body = Problem.builder()
                    .timeStamp(OffsetDateTime.now())
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .userMessage(GENERIC_ERROR_MSG)
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .timeStamp(OffsetDateTime.now())
                    .title((String) body)
                    .status(status.value())
                    .userMessage(GENERIC_ERROR_MSG)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem.ProblemBuilder createApiErrorBuilder(HttpStatus status, ApiErrorType apiErrorType, String detail) {
        return Problem.builder()
                .timeStamp(OffsetDateTime.now())
                .status(status.value())
                .type(apiErrorType.getUri())
                .title(apiErrorType.getTitle())
                .timeStamp(OffsetDateTime.now())
                .detail(detail);
    }

    private String joinPath(List<Reference> references) {
        return references.stream()
                .map(Reference::getFieldName)
                .collect(Collectors.joining("."));
    }
}
