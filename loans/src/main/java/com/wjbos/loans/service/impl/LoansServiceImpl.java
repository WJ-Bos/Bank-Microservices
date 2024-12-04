package com.wjbos.loans.service.impl;

import com.wjbos.loans.constants.LoanConstants;
import com.wjbos.loans.dto.LoanDto;
import com.wjbos.loans.entity.Loans;
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
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan","Mobile Number",mobileNumber)
        );
        LoanDto loansDto = LoansMapper.mapToLoanDto(new LoanDto(), loans);
        return loansDto;
    }

    @Override
    public boolean createLoanByMobileNumber(String mobileNumber) {
        Optional<Loans> loansEntity = loansRepository.findByMobileNumber(mobileNumber);

        if(loansEntity.isPresent()) {
            throw new LoanAlreadyExistsException("Loan","Mobile Number",mobileNumber);
        }else{
            loansRepository.save(createLoan(mobileNumber));
        }
        return true;
    }

    private Loans createLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType("Home Loan");
        newLoan.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    @Override
    public boolean updateLoan(LoanDto loansDto) {
        Loans loans = loansRepository.findByMobileNumber(loansDto.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan","Mobile Number",loansDto.getMobileNumber())
        );
        LoansMapper.mapToLoanEntity(loansDto, loans);
        loansRepository.save(loans);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan","Mobile Number",mobileNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }
}
