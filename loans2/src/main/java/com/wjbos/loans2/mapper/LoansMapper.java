package com.wjbos.loans2.mapper;

import com.wjbos.loans.dto.LoanDto;
import com.wjbos.loans.entity.LoansEntity;

public class LoansMapper {

    public static LoanDto mapToLoanDto(LoanDto loanDto, LoansEntity loansEntity){
        loanDto.setMobileNumber(loansEntity.getMobileNumber());
        loanDto.setLoanType(loansEntity.getLoanType());
        loanDto.setAmountPaid(loansEntity.getAmountPaid());
        loanDto.setOutstandingAmount(loansEntity.getOutstandingAmount());
        loanDto.setLoanNumber(loansEntity.getLoanNumber());
        return loanDto;
    }

    public static LoansEntity mapToLoanEntity(LoanDto loanDto, LoansEntity loansEntity){
        loansEntity.setMobileNumber(loanDto.getMobileNumber());
        loansEntity.setLoanType(loanDto.getLoanType());
        loansEntity.setAmountPaid(loanDto.getAmountPaid());
        loansEntity.setOutstandingAmount(loanDto.getOutstandingAmount());
        loansEntity.setLoanNumber(loanDto.getLoanNumber());
        return loansEntity;
    }
}
