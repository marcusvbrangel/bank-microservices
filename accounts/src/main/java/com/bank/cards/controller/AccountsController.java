package com.bank.cards.controller;

import com.bank.cards.constants.AccountsConstants;
import com.bank.cards.dto.AccountsContactInfoDto;
import com.bank.cards.dto.CustomerAccountDto;
import com.bank.cards.dto.CustomerDto;
import com.bank.cards.dto.ResponseSuccessDto;
import com.bank.cards.service.AccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/accounts")
@Validated
public class AccountsController {

    private AccountsService accountsService;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

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

    @DeleteMapping("/{mobileNumber}")
    public ResponseEntity<ResponseSuccessDto> deleteAccountByMobileNumber(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @PathVariable String mobileNumber) {

        accountsService.deleteAccountByMobileNumber(mobileNumber);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ResponseSuccessDto(HttpStatus.NO_CONTENT.toString(), "Account deleted successfully"));

    }

    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoDto);
    }

}
