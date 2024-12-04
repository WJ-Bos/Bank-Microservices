package com.wjbos.loans.controller;

import com.wjbos.loans.constants.LoanConstants;
import com.wjbos.loans.dto.ErrorResponseDto;
import com.wjbos.loans.dto.LoanDto;
import com.wjbos.loans.dto.LoansContactInfoDto;
import com.wjbos.loans.dto.ResponseDto;
import com.wjbos.loans.service.impl.LoansServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(
        name = "CRUD REST APIs for Loans in The Banking System",
        description = "CRUD REST APIs in Banking System to CREATE, UPDATE, FETCH AND DELETE loan details"
)

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(path = "/api",produces = APPLICATION_JSON_VALUE)
public class LoansController {

    private final LoansServiceImpl loansService;

    @Autowired
    private LoansContactInfoDto loansContactInfoDto;

    @Value("${build.version}")
    private String buildVersion;

    @Operation(
        summary = "Endpoint for creating a Loan",
            description = "Checks to see if a User already has a Loan tied to their\n Mobile number" +
                    "and if not then a Loan can be created."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Https status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
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

    @Operation(
            summary = "Allow User to fetch Loans tied to a Account",
            description = "Check to see if there is a Loan tied to the mobile Number.\n" +
                    "if there Is then the Loan is retrieved"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<LoanDto> fetchLoan(@RequestParam
                                             @Pattern(regexp = "(^$|[0-9]{10})",
                                                     message = "Mobile Number needs to be 10 digits long")
                                             String mobileNumber){
        LoanDto loansDto = loansService.fetchAccountLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loansDto);
    }



    @Operation(
            summary = "Allow a user to Update their Loan details",
            description = "Check to see if the loan exists and If it does override the existing value with the " +
                    "new DTO values and save"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status Successful operation"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP Status Update operation Failed",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoanDto loansDto){
        boolean isUpdated = loansService.updateLoan(loansDto);

        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoanConstants.STATUS_201, LoanConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstants.STATUS_417,LoanConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            description = "Endpoint to delete a Loan",
            summary = "Allows users to delete a Loan vis a Mobile Number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status Successful"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP Status Delete Failed",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoan(@RequestParam
                                                     @Pattern(regexp = "(^$|[0-9]{10})",
                                                             message = "Mobile Number needs to be 10 digits long")
                                                     String mobileNumber){
        boolean isDeleted = loansService.deleteLoan(mobileNumber);

        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoanConstants.MESSAGE_200,LoanConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstants.STATUS_417,LoanConstants.MESSAGE_417_DELETE));
        }

    }

    @Operation(
            summary = "Fetches the Build info fore Loans microservice",
            description = "Returns the Build info when a User requests it."
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Http status OK",
                    responseCode = "200"
            ),
            @ApiResponse(
                    description = "Http Status Error",
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @GetMapping("/contact-info")
    public ResponseEntity<LoansContactInfoDto> getCardsContactInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(loansContactInfoDto);
    }

}
