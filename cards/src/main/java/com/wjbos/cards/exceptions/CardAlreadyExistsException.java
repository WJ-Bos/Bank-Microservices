package com.wjbos.cards.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CardAlreadyExistsException extends RuntimeException {
    public CardAlreadyExistsException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("A %s with field %s already exists for : %s", resourceName, fieldName,fieldValue));
    }
}
