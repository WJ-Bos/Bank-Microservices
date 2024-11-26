package com.wjbos.loans.exception;

public class LoanAlreadyExistsException extends RuntimeException {
    public LoanAlreadyExistsException(String resourceName, String fieldName, String fieldValue)
    {
        super(String.format("A %s with field %s already exists for : %s", resourceName, fieldName,fieldValue));
    }
}