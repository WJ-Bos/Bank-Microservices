package com.wjbos.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Data Transfer Object for the Customer"
)
public class CustomerDto {

    @NotEmpty(message = "The Name field can not be empty")
    @Size(min = 5, max = 30 ,message = "Note that a name can only be between 5 and 30 characters")
    private String name;

    @NotEmpty(message = "The email field can not be empty")
    @Email(message = "Email should follow standard email format")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number needs to be 10 digits")
    private String mobileNumber;

    private AccountsDto accountsDto;
}
