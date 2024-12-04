package com.wjbos.cards.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;

@ConfigurationProperties(prefix = "cards")
public record CardsContactInfoDto(
        String message,
        HashMap<String,String>contactDetails,
        String onCallSupport
) {
}
