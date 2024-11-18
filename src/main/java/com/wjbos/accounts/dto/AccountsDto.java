package com.wjbos.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {

    @NotEmpty(message = "Account number can not be empty")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Account number needs to be 10 digits long" )
    private Long accountNumber;

    @NotEmpty(message = "account type can nt be null or empty")
    private String accountType;

    @NotEmpty(message = "Branch address can nt be null or empty")
    private String branchAddress;
}
