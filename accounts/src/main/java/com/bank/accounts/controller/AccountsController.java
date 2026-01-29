package com.bank.accounts.controller;

import com.bank.accounts.constants.AccountsConstants;
import com.bank.accounts.dto.CustomerAccountDto;
import com.bank.accounts.dto.CustomerDto;
import com.bank.accounts.dto.ResponseSuccessDto;
import com.bank.accounts.service.AccountsService;
import jakarta.validation.Valid;
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
public class AccountsController {

    private AccountsService accountsService;

    @PostMapping
    public ResponseEntity<ResponseSuccessDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {

        accountsService.createAccount(customerDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseSuccessDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));

    }

    @GetMapping("/{mobileNumber}")
    public ResponseEntity<CustomerAccountDto> fetchAccountByMobileNumber(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @PathVariable String mobileNumber) {
        CustomerAccountDto customerAccountDto = accountsService.fetchAccountByMobileNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerAccountDto);
    }

    @PutMapping
    public ResponseEntity<ResponseSuccessDto> updateAccount(@Valid @RequestBody CustomerAccountDto customerAccountDto) {

        boolean isUpdated = accountsService.updateAccount(customerAccountDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseSuccessDto(AccountsConstants.STATUS_200, "Account updated successfully"));

    }

    @DeleteMapping("/{mobileNumber}")
    public ResponseEntity<ResponseSuccessDto> deleteAccount(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @PathVariable String mobileNumber) {

        boolean isDeleted = accountsService.deleteAccount(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseSuccessDto(AccountsConstants.STATUS_200, "Account deleted successfully"));

    }

}

