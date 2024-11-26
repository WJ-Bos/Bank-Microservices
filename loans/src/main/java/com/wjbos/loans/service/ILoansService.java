package com.wjbos.loans.service;


import com.wjbos.loans.dto.LoanDto;

public interface ILoansService {
    LoanDto fetchAccountLoan(String mobileNumber);

    boolean createLoanByMobileNumber(String mobileNumber);
}
