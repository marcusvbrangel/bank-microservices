package com.bank.cards.controller;

import com.bank.cards.constants.AccountsConstants;
import com.bank.cards.dto.CustomerAccountDto;
import com.bank.cards.dto.CustomerDto;
import com.bank.cards.dto.ResponseErrorDto;
import com.bank.cards.dto.ResponseSuccessDto;
import com.bank.cards.service.AccountsService;
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
        name = "CRUD REST API for Accounts in WolfBank",
        description = "CRUD REST API in WolfBank to CREATE / READ / UPDATE / DELETE cards details"
)
public class AccountsController {

    private AccountsService accountsService;

    @PostMapping
    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create a new Customer and Account inside WolfBank"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
    )
    public ResponseEntity<ResponseSuccessDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {

        accountsService.createAccount(customerDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseSuccessDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));

    }

    @GetMapping("/{mobileNumber}")
    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch Customer and Account details base on a mobile number"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    public ResponseEntity<CustomerAccountDto> fetchAccountByMobileNumber(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @PathVariable String mobileNumber) {
        CustomerAccountDto customerAccountDto = accountsService.fetchAccountByMobileNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerAccountDto);
    }

    @PutMapping
    @Operation(
            summary = "Update Account Details REST API",
            description = "REST API to update Customer and Account details base on a mobile number"
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
    public ResponseEntity<ResponseSuccessDto> updateAccount(@Valid @RequestBody CustomerAccountDto customerAccountDto) {

        accountsService.updateAccount(customerAccountDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseSuccessDto(AccountsConstants.STATUS_200, "Account updated successfully"));

    }

    @DeleteMapping("/{mobileNumber}")
    @Operation(
            summary = "Delete Account and Customer Details REST API",
            description = "REST API to delete Customer and Account details base on a mobile number"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error"
    )
    public ResponseEntity<ResponseSuccessDto> deleteAccount(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @PathVariable String mobileNumber) {

        boolean isDeleted = accountsService.deleteAccount(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseSuccessDto(AccountsConstants.STATUS_200, "Account deleted successfully"));

    }

}

