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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/accounts")
@AllArgsConstructor
@Validated
@Tag(
        name = "CRUD REST API for Accounts in WolfBank",
        description = "CRUD REST API in WolfBank to CREATE / READ / UPDATE / DELETE cards details"
)
public class AccountsController {

    private AccountsService accountsService;

    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create a new Customer and Account inside WolfBank"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
    )
    @PostMapping
    public ResponseEntity<ResponseSuccessDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {

        accountsService.createAccount(customerDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseSuccessDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));

    }

    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch Customer and Account details base on a mobile number"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @GetMapping("/{mobileNumber}")
    public ResponseEntity<CustomerAccountDto> fetchAccountByMobileNumber(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @PathVariable String mobileNumber) {
        CustomerAccountDto customerAccountDto = accountsService.fetchAccountByMobileNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerAccountDto);
    }

    @Operation(
            summary = "Update Account Details REST API",
            description = "REST API to update Customer and Account details base on a mobile number"
    )

    @ApiResponse(
            responseCode = "204",
            description = "HTTP Status NO_CONTENT"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                    schema = @Schema(implementation = ResponseErrorDto.class)
            )
    )
    @PutMapping("/{accountNumber}")
    public ResponseEntity<ResponseSuccessDto> updateAccountByAccountNumber(
            @NotEmpty(message = "Account number can not be null or empty")
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digits")
            @PathVariable String accountNumber,
            @Valid @RequestBody CustomerAccountDto customerAccountDto) {

        accountsService.updateAccountByAccountNumber(accountNumber, customerAccountDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ResponseSuccessDto(HttpStatus.NO_CONTENT.toString(), "Account updated successfully"));

    }

    @Operation(
            summary = "Delete Account and Customer Details REST API",
            description = "REST API to delete Customer and Account details base on a mobile number"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status NO_CONTENT"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error"
    )
    @DeleteMapping("/{mobileNumber}")
    public ResponseEntity<ResponseSuccessDto> deleteAccountByMobileNumber(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @PathVariable String mobileNumber) {

        accountsService.deleteAccountByMobileNumber(mobileNumber);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ResponseSuccessDto(HttpStatus.NO_CONTENT.toString(), "Account deleted successfully"));

    }

}

