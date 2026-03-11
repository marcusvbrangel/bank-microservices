package com.bank.accounts.controller;

import com.bank.accounts.dto.LoansContactInfoDto;
import com.bank.accounts.dto.LoansDto;
import com.bank.accounts.dto.ResponseSuccessDto;
import com.bank.accounts.service.LoansService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/loans")
@Validated
public class LoansController {

    private LoansService loansService;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private LoansContactInfoDto accountsContactInfoDto;

    public LoansController(LoansService loansService) {
        this.loansService = loansService;
    }

    @PostMapping
    public ResponseEntity<ResponseSuccessDto> createLoan(@Valid @RequestBody LoansDto loansDto) {

        loansService.createLoan(loansDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseSuccessDto(HttpStatus.CREATED.toString(), "Loan created with success"));

    }

    @GetMapping("/{mobileNumber}")
    public ResponseEntity<LoansDto> fetchLoanByMobileNumber(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @PathVariable String mobileNumber) {
        LoansDto loansDto = loansService.fetchLoanByMobileNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(loansDto);
    }

    @PutMapping
    public ResponseEntity<ResponseSuccessDto> updateLoan(@Valid @RequestBody LoansDto loansDto) {

        loansService.updateLoan(loansDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseSuccessDto(HttpStatus.OK.toString(), "Loan updated successfully"));

    }

    @DeleteMapping("/{mobileNumber}")
    public ResponseEntity<ResponseSuccessDto> deleteLoan(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @PathVariable String mobileNumber) {

        boolean isDeleted = loansService.deleteLoan(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseSuccessDto(HttpStatus.OK.toString(), "Loan deleted successfully"));

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
    public ResponseEntity<LoansContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoDto);
    }

}
