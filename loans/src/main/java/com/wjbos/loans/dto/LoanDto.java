package com.wjbos.loans.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoanDto {

    @NotEmpty(message = "Mobile number can not be empty")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile Number needs to be 10 digits long" )
    private String mobileNumber;
    @NotEmpty(message = "Loan number can not be empty")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Loan Number needs to be 10 digits long" )
    private String loanNumber;

    @NotEmpty(message = "Loan type can not be empty")
    private String loanType;

    @NotEmpty(message = "Total loan can not be empty")
    private int totalLoan;

    @NotEmpty(message = "Amount paid can not be empty")
    private int amountPaid;

    @NotEmpty(message = "Outstanding amount can not be empty")
    private int outstandingAmount;

}
