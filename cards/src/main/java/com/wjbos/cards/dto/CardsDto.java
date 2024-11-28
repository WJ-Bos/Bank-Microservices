package com.wjbos.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(name = "Cards",
        description = "Schema to hold Card information"
)
@Data
public class CardsDto {

    @NotEmpty(message = "Mobile number can not be Empty")
    @Schema(
            description = "Mobile Number for User",
            example = "087654321"
    )
    private String mobileNumber;

    @NotEmpty(message = "Card Number is not allowed to empty")
    private String cardNumber;

    @NotEmpty(message = "The Card number is not allowed to empty")
    @Schema(
            description = "Card types that can be chosen",
            example = "Credit Card"
    )
    private String cardType;

    @Positive(message = "Total card limit needs to be greater than Zero")
    private int totalLimit;

    @PositiveOrZero(message = "Total Amount needs to be equal or greater than zero")
    private int amountUsed;

    @PositiveOrZero(message = "Available Amount needs to be equal or greater than zero")
    private int availableAmount;
}
