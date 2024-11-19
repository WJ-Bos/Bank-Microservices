package com.wjbos.loans2.service;


import com.wjbos.loans.dto.LoanDto;

public interface ILoansService {
    LoanDto fetchAccountLoan(String mobileNumber);
}
