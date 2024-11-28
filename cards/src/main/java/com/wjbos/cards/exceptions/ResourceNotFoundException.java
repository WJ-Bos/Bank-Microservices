package com.wjbos.cards.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource,String parameter,String value) {
        super(String.format("Resource %s not found for parameter %s: %s" ,resource,parameter,value ));
    }
}
