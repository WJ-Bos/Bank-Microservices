package com.wjbos.loans.mapper;

import com.wjbos.loans.dto.LoanDto;
import com.wjbos.loans.entity.Loans;

public class LoansMapper {

    public static LoanDto mapToLoanDto(LoanDto loanDto, Loans loans){
        loanDto.setMobileNumber(loans.getMobileNumber());
        loanDto.setLoanType(loans.getLoanType());
        loanDto.setAmountPaid(loans.getAmountPaid());
        loanDto.setOutstandingAmount(loans.getOutstandingAmount());
        loanDto.setLoanNumber(loans.getLoanNumber());
        return loanDto;
    }

    public static Loans mapToLoanEntity(LoanDto loanDto, Loans loans){
        loans.setMobileNumber(loanDto.getMobileNumber());
        loans.setLoanType(loanDto.getLoanType());
        loans.setAmountPaid(loanDto.getAmountPaid());
        loans.setOutstandingAmount(loanDto.getOutstandingAmount());
        loans.setLoanNumber(loanDto.getLoanNumber());
        return loans;
    }
}
