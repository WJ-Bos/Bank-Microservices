package com.wjbos.cards.exceptions;

import com.wjbos.cards.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String validationMessage = error.getDefaultMessage();
            validationErrors.put(fieldName,validationMessage);
        });
        return new ResponseEntity<>(validationErrors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<ErrorResponseDto> handleCardAlreadyExistsException(CardAlreadyExistsException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception, WebRequest request){

        ErrorResponseDto responseDto = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseDto,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
