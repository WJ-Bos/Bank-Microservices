package com.wjbos.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(
        name = "Account",
        description = "Data Transfer Object for the Account"
)
public class AccountsDto {

    @NotEmpty(message = "Account number can not be empty")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Account number needs to be 10 digits long" )
    private Long accountNumber;

    @NotEmpty(message = "account type can nt be null or empty")
    private String accountType;

    @NotEmpty(message = "Branch address can nt be null or empty")
    private String branchAddress;
}
