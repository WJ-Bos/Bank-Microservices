package com.wjbos.loans.service;


import com.wjbos.loans.dto.LoanDto;
import jakarta.validation.Valid;

public interface ILoansService {
    LoanDto fetchAccountLoan(String mobileNumber);

    boolean createLoanByMobileNumber(String mobileNumber);
    boolean updateLoan(@Valid LoanDto loansDto);
}
