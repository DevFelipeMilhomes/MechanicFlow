package com.devfelipemilhomes.exception;

import com.devfelipemilhomes.client.exception.DuplicateCpfException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handlerGenericException(Exception e){
        ErrorResponseDTO error = ErrorResponseDTO.responseDefault("An internal server error occurred");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErrorsField> fieldsErrorsList = fieldErrors.stream().map(fe -> new ErrorsField(fe.getField(),fe.getDefaultMessage()))
                .collect(Collectors.toList());
        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),"Validation error", OffsetDateTime.now(), fieldsErrorsList);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlerResourceNotFoundException(ResourceNotFoundException e){
        ErrorResponseDTO error = ErrorResponseDTO.responseNotFound(e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DuplicateCpfException.class)
    public ResponseEntity<ErrorResponseDTO> handlerDuplicateCpfException(DuplicateCpfException e){
        ErrorResponseDTO error = ErrorResponseDTO.responseConflict("CPF already registered");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
