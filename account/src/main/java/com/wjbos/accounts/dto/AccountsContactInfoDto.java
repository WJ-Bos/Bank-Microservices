package com.wjbos.accounts.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;

@ConfigurationProperties(prefix = "accounts")
public record AccountsContactInfoDto(
        String message,
        HashMap<String,String> contactDetails,
        String onCallSupport
) {
}
