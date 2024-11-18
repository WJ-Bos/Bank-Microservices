package com.wjbos.accounts.exception;

import com.wjbos.accounts.dto.ErrorResponseDto;
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
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * This method is invoked when a controller method parameter is invalid.
     * It is called before the controller method is invoked.
     * It sets the HTTP status code to 400 (Bad Request) and returns a {@link ResponseEntity}
     * containing a map of validation errors.
     *
     * @param ex     the exception that was thrown
     * @param headers the HttpHeaders to be written to the response
     * @param status the HTTP status of the response
     * @param request the current request
     * @return a ResponseEntity containing a map of validation errors
     */
    @Override
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

    /**
     * Handle CustomerAlreadyExistsException
     *
     * @param exception the exception that was thrown
     * @param request the web request
     * @return a ResponseEntity containing an ErrorResponseDto
     */
    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException exception, WebRequest request) {
        ErrorResponseDto responseDto = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseDto,HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle ResourceNotFoundExceptions.
     *
     * @param exception the exception that was thrown
     * @param request the web request
     * @return a ResponseEntity containing an ErrorResponseDto
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
        ErrorResponseDto responseDto = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseDto,HttpStatus.NOT_FOUND);
    }

    /**
     * Handles all other Exceptions and returns a generic 500 error response
     *
     * @param exception the exception that was thrown
     * @param request the web request
     * @return a ResponseEntity containing an ErrorResponseDto
     */
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
