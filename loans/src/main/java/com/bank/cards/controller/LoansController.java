package com.bank.cards.controller;

import com.bank.cards.dto.LoansDto;
import com.bank.cards.dto.ResponseErrorDto;
import com.bank.cards.dto.ResponseSuccessDto;
import com.bank.cards.service.LoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/cards")
@AllArgsConstructor
@Validated
@Tag(
    name = "CRUD REST API for Loans in WolfBank",
    description = "CRUD REST API in WolfBank to CREATE / READ / UPDATE / DELETE loan details"
)
public class LoansController {

    private LoansService loansService;

    @PostMapping
    @Operation(
        summary = "Create Loan REST API",
        description = "REST API to create a new Loan inside WolfBank"
    )
    @ApiResponse(
        responseCode = "201",
        description = "HTTP Status CREATED"
    )
    public ResponseEntity<ResponseSuccessDto> createLoan(@Valid @RequestBody LoansDto loansDto) {

        loansService.createLoan(loansDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseSuccessDto(HttpStatus.CREATED.toString(), "Loan created with success"));

    }

    @GetMapping("/{mobileNumber}")
    @Operation(
        summary = "Fetch Loan Details REST API",
        description = "REST API to fetch Loan details based on a mobile number"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status OK"
    )
    public ResponseEntity<LoansDto> fetchLoanByMobileNumber(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @PathVariable String mobileNumber) {
        LoansDto loansDto = loansService.fetchLoanByMobileNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(loansDto);
    }

    @PutMapping
    @Operation(
        summary = "Update Loan Details REST API",
        description = "REST API to update Loan based on a mobile number"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status OK"
    )
    @ApiResponse(
        responseCode = "500",
        description = "HTTP Status Internal Server Error",
        content = @Content(
            schema = @Schema(implementation = ResponseErrorDto.class)
        )
    )
    public ResponseEntity<ResponseSuccessDto> updateLoan(@Valid @RequestBody LoansDto loansDto) {

        loansService.updateLoan(loansDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseSuccessDto(HttpStatus.OK.toString(), "Loan updated successfully"));

    }

    @DeleteMapping("/{mobileNumber}")
    @Operation(
        summary = "Delete Loan Details REST API",
        description = "REST API to delete Loan details based on a mobile number"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status OK"
    )
    @ApiResponse(
        responseCode = "500",
        description = "HTTP Status Internal Server Error"
    )
    public ResponseEntity<ResponseSuccessDto> deleteLoan(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @PathVariable String mobileNumber) {

        boolean isDeleted = loansService.deleteLoan(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseSuccessDto(HttpStatus.OK.toString(), "Loan deleted successfully"));

    }

}

