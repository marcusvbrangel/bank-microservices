package com.bank.accounts.controller;

import com.bank.accounts.dto.LoansDto;
import com.bank.accounts.dto.ResponseErrorDto;
import com.bank.accounts.dto.ResponseSuccessDto;
import com.bank.accounts.service.LoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "CRUD REST APIs for Loans in WolfBank",
    description = "CRUD REST APIs in WolfBank to CREATE, UPDATE, FETCH AND DELETE loans details"
)
@RestController
@RequestMapping(value = "/api/v1/loans")
@Validated
public class LoansController {

    private final LoansService loansService;

    public LoansController(LoansService loansService) {
        this.loansService = loansService;
    }

    @Operation(
        summary = "Create Card REST API",
        description = "REST API to create new Loan inside WolfBank"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "HTTP Status Bad Request"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                schema = @Schema(implementation = ResponseErrorDto.class)
            )
        )
    })
    @PostMapping
    public ResponseEntity<ResponseSuccessDto> createLoan(@Valid @RequestBody LoansDto loansDto) {

        loansService.createLoan(loansDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @Operation(
        summary = "Fetch Loan Details REST API",
        description = "REST API to fetch Loan details based on a mobile number"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "HTTP Status Bad Request"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "HTTP Status Not Found"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                schema = @Schema(implementation = ResponseErrorDto.class)
            )
        )
    })
    @GetMapping("/{mobileNumber}")
    public ResponseEntity<LoansDto> fetchLoanByMobileNumber(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @PathVariable String mobileNumber) {

        LoansDto loansDto = loansService.fetchLoanByMobileNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loansDto);
    }

    @Operation(
        summary = "Update Loan Details REST API",
        description = "REST API to Update Loan details based on a mobile number"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "HTTP Status No Content"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "HTTP Status Bad Request"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "HTTP Status Not Found"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                schema = @Schema(implementation = ResponseErrorDto.class)
            )
        )
    })
    @PutMapping("/{mobileNumber}")
    public ResponseEntity<ResponseSuccessDto> updateLoan(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") @PathVariable String mobileNumber,
            @Valid @RequestBody LoansDto loansDto) {

        loansService.updateLoan(mobileNumber, loansDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @Operation(
        summary = "Delete Loan Details REST API",
        description = "REST API to Delete Loan details based on a mobile number"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "HTTP Status No Content"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "HTTP Status Bad Request"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "HTTP Status Not Found"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                schema = @Schema(implementation = ResponseErrorDto.class)
            )
        )
    })
    @DeleteMapping("/{mobileNumber}")
    public ResponseEntity<ResponseSuccessDto> deleteLoan(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @PathVariable String mobileNumber) {

        loansService.deleteLoan(mobileNumber);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
