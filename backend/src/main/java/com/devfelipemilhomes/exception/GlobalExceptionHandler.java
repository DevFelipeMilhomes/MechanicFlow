package com.devfelipemilhomes.exception;

import com.devfelipemilhomes.serviceOrder.dto.ReservationNotYetReturned;
import com.devfelipemilhomes.serviceOrder.exception.StatusReturnNotAllowed;
import com.devfelipemilhomes.stock.exception.ConsumptionExceedingReserves;
import com.devfelipemilhomes.stock.exception.QuantityReleaseExceedingUnconsumed;
import com.devfelipemilhomes.stock.exception.ReservationExceedingQuantityHand;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlerResourceNotFoundException(ResourceNotFoundException e){
        ErrorResponseDTO error = ErrorResponseDTO.responseNotFound(e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DuplicateFieldException.class)
    public ResponseEntity<ErrorResponseDTO> handlerDuplicateFieldException(DuplicateFieldException e){
        ErrorResponseDTO error = ErrorResponseDTO.responseConflict(e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(ReservationExceedingQuantityHand.class)
    public ResponseEntity<ErrorResponseDTO> handlerReservationExceedingQuantityHand(ReservationExceedingQuantityHand e){
        ErrorResponseDTO error = ErrorResponseDTO.responseConflict(e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(ConsumptionExceedingReserves.class)
    public ResponseEntity<ErrorResponseDTO> handlerConsumptionExceedingReserves(ConsumptionExceedingReserves e){
        ErrorResponseDTO error = ErrorResponseDTO.responseConflict(e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(StatusReturnNotAllowed.class)
    public ResponseEntity<ErrorResponseDTO> handlerStatusReturnNotAllowed(StatusReturnNotAllowed e){
        ErrorResponseDTO error = ErrorResponseDTO.responseConflict(e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(ReservationNotYetReturned.class)
    public ResponseEntity<ErrorResponseDTO> handlderReservationNotYetReturned(ReservationNotYetReturned e){
        ErrorResponseDTO error = ErrorResponseDTO.responseConflict(e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(QuantityReleaseExceedingUnconsumed.class)
    public ResponseEntity<ErrorResponseDTO> handlderQuantityReleaseExceedingUnconsumed(QuantityReleaseExceedingUnconsumed e){
        ErrorResponseDTO error = ErrorResponseDTO.responseConflict(e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handlerHttpMessageNotReadableException(HttpMessageNotReadableException e){
        ErrorResponseDTO error = ErrorResponseDTO.responseBadRequest("The request body could not be read");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDTO> handlerHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e,
            HttpServletRequest request){
        String message = String.format(
                "HTTP method '%s' is not supported for this endpoint",
                e.getMethod()
        );
        ErrorResponseDTO error = ErrorResponseDTO.responseMethodNotAllowed(message, request.getRequestURI());

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlerNoResourceFoundException(NoResourceFoundException e){
        ErrorResponseDTO error = ErrorResponseDTO.responseNotFound("Resource not found");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
