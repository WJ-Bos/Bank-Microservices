package com.wjbos.loans2.service.impl;

import com.wjbos.loans.dto.LoanDto;
import com.wjbos.loans.entity.LoansEntity;
import com.wjbos.loans.exception.ResourceNotFoundException;
import com.wjbos.loans.mapper.LoansMapper;
import com.wjbos.loans.repo.LoansRepository;
import com.wjbos.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

    LoansRepository loansRepository;

    @Override
    public LoanDto fetchAccountLoan(String mobileNumber) {
        LoansEntity loansEntity = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan","Mobile Number",mobileNumber)
        );
        LoanDto loansDto = LoansMapper.mapToLoanDto(new LoanDto(),loansEntity);
        return loansDto;
    }
}
