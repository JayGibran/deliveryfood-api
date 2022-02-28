package com.jaygibran.deliveryfood.api.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ApiError {

    private LocalDateTime dateTime;
    private String message;
}
