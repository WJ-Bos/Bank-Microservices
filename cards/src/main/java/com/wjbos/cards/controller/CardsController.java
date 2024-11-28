package com.wjbos.cards.controller;

import com.wjbos.cards.constants.CardsConstants;
import com.wjbos.cards.dto.CardsDto;
import com.wjbos.cards.dto.ErrorResponseDto;
import com.wjbos.cards.dto.ResponseDto;
import com.wjbos.cards.service.impl.CardsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Tag(
        name = "REST API for CRUD Operations for Cards",
        description = "CRUD REST API's for bank Cards and Card details"
)
@RestController
@RequestMapping(path = "/api", produces = APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class CardsController {

    private CardsServiceImpl cardsService;

    @Operation(
            description = "Creation endpoint for Creating a New Card for a User",
            summary ="This will check to se eif the card already exists and If not" +
                    "then the Card will be Initiated to the Mobile Number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "HTTPS 201 Created",
                    description = "Card Created Successfully"
            ),
            @ApiResponse(
                    responseCode = "HTTPS 417",
                    description = "Failure to create Card",
                    content = @Content(
                            schema = @Schema(implementation =ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@RequestParam
                                                  @Pattern(regexp = "(^$|[0-9]{10})",
                                                          message = "Mobile Number needs to be 10 digits long")
                                                  String mobileNumber){
        cardsService.createCardByMobileNumber(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201,CardsConstants.MESSAGE_201));
    }

    @Operation(
            description = "Fetch endpoint to return a Card and its Details",
            summary ="Check to see if the cards exists , if it does," +
                    "return it and if it doesnt throw a new error."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "HTTPS 200 Successful Operation",
                    description = "Fetch operation Successful"
            ),
            @ApiResponse(
                    responseCode = "HTTPS 500",
                    description = "Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation =ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping(path = "/fetch")
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestParam
                                                     @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number needs to be 10 digits")
                                                     String mobileNumber) {
        CardsDto cardsDto = cardsService.fetchByMobileNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
    }

    @Operation(
            description = "",
            summary =""
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "HTTPS 200 Success",
                    description = "Updated Successfully"
            ),
            @ApiResponse(
                    responseCode = "HTTPS 417",
                    description = "Failure to Update Card Details",
                    content = @Content(
                            schema = @Schema(implementation =ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCardDetails(@Valid @RequestBody CardsDto cardsDto) {
        boolean isUpdated = cardsService.updateCardDetails(cardsDto);
        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(CardsConstants.STATUS_200,CardsConstants.MESSAGE_200));
        }else {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(CardsConstants.STATUS_417,CardsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            description = "Endpoint to Delete a Card that Exists",
            summary ="Checks to see if a Card exists, if it does then it is deleted."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "HTTPS 200 Deleted Successful",
                    description = "Card Created Successfully"
            ),
            @ApiResponse(
                    responseCode = "HTTPS 417",
                    description = "Failure to Delete Card",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCard(@RequestParam
                                                  @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number needs to be 10 digits")
                                                  String mobileNumber) {
       boolean isDeleted =  cardsService.deleteByMobileNumber(mobileNumber);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(CardsConstants.STATUS_200,CardsConstants.MESSAGE_200));
        }else{
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(CardsConstants.STATUS_417,CardsConstants.MESSAGE_417_DELETE));
        }
    }

}
