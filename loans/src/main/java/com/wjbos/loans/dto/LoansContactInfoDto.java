package com.wjbos.loans.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;

@ConfigurationProperties(prefix = "cards")
public record LoansContactInfoDto(
        String message,
        HashMap<String,String> contactDetails,
        String onCallSupport
) {
}

