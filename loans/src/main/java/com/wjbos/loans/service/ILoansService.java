package com.wjbos.loans.service;


import com.wjbos.loans.dto.LoanDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

public interface ILoansService {
    LoanDto fetchAccountLoan(String mobileNumber);

    boolean createLoanByMobileNumber(String mobileNumber);
    boolean updateLoan(@Valid LoanDto loansDto);

    boolean deleteLoan(@Pattern(regexp = "(^$|[0-9]{10})",
                                                             message = "Mobile Number needs to be 10 digits long") String mobileNumber);
}
