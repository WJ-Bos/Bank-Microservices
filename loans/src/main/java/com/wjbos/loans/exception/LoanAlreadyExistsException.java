package com.wjbos.loans.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LoanAlreadyExistsException extends RuntimeException {

    public LoanAlreadyExistsException(String resourceName, String fieldName, String fieldValue)
    {
        super(String.format("A %s with field %s already exists for : %s", resourceName, fieldName,fieldValue));
    }
}
