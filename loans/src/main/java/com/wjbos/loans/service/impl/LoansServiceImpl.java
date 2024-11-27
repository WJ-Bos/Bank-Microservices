package com.wjbos.loans.service.impl;

import com.wjbos.loans.constants.LoanConstants;
import com.wjbos.loans.dto.LoanDto;
import com.wjbos.loans.entity.LoansEntity;
import com.wjbos.loans.exception.LoanAlreadyExistsException;
import com.wjbos.loans.exception.ResourceNotFoundException;
import com.wjbos.loans.mapper.LoansMapper;
import com.wjbos.loans.repo.LoansRepository;
import com.wjbos.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

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

    @Override
    public boolean createLoanByMobileNumber(String mobileNumber) {
        Optional<LoansEntity> loansEntity = loansRepository.findByMobileNumber(mobileNumber);

        if(loansEntity.isPresent()) {
            throw new LoanAlreadyExistsException("Loan","Mobile Number",mobileNumber);
        }else{
            loansRepository.save(createLoan(mobileNumber));
        }
        return true;
    }

    private LoansEntity createLoan(String mobileNumber) {
        LoansEntity newLoan = new LoansEntity();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoanConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    @Override
    public boolean updateLoan(LoanDto loansDto) {
        LoansEntity loansEntity = loansRepository.findByMobileNumber(loansDto.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan","Mobile Number",loansDto.getMobileNumber())
        );
        LoansMapper.mapToLoanEntity(loansDto,loansEntity);
        loansRepository.save(loansEntity);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        LoansEntity loansEntity = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan","Mobile Number",mobileNumber)
        );
        loansRepository.deleteById(loansEntity.getLoanId());
        return true;
    }
}
