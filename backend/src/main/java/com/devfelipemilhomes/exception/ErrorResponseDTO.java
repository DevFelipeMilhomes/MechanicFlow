package com.devfelipemilhomes.exception;

import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public record ErrorResponseDTO(int status, String message, OffsetDateTime date, List<ErrorsField> errors) {
    public static ErrorResponseDTO responseDefault(String message){
        return new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),message,OffsetDateTime.now(),List.of());
    }

    public static ErrorResponseDTO responseNotFound(String message){
        return new ErrorResponseDTO(HttpStatus.NOT_FOUND.value(), message, OffsetDateTime.now(), List.of());
    }

    public static ErrorResponseDTO responseConflict(String message){
        return new ErrorResponseDTO(HttpStatus.CONFLICT.value(), message, OffsetDateTime.now(), List.of());
    }

    public static ErrorResponseDTO responseBadRequest(String message){
        return new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), message, OffsetDateTime.now(), List.of());
    }

    public static ErrorResponseDTO responseMethodNotAllowed(String message, String path){
        ErrorsField errorsField = new ErrorsField("path",path);
        List<ErrorsField> errors = new ArrayList<>();
        errors.add(errorsField);
        return new ErrorResponseDTO(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                message,
                OffsetDateTime.now(),
                errors
                );
    }
}
