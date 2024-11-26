package com.wjbos.loans.controller;

import com.wjbos.loans.constants.LoanConstants;
import com.wjbos.loans.dto.LoanDto;
import com.wjbos.loans.dto.ResponseDto;
import com.wjbos.loans.service.impl.LoansServiceImpl;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping(path = "/api",produces = APPLICATION_JSON_VALUE)
public class LoansController {

    LoansServiceImpl loansService;

    @GetMapping("/fetch")
    public ResponseEntity<LoanDto> fetchLoan(@RequestParam
                                                @Pattern(regexp = "(^$|[0-9]{10})",
                                                        message = "Mobile Number needs to be 10 digits long")
                                                String mobileNumber){
        LoanDto loansDto = loansService.fetchAccountLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loansDto);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam
                                                      @Pattern(regexp = "(^$|[0-9]{10})",
                                                              message = "Mobile Number needs to be 10 digits long")
                                                      String mobileNumber){
        loansService.createLoanByMobileNumber(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(LoanConstants.STATUS_201, LoanConstants.MESSAGE_201));
    }
}
