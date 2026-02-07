package com.bank.cards.controller;

import com.bank.cards.dto.LoansDto;
import com.bank.cards.dto.ResponseSuccessDto;
import com.bank.cards.service.LoansService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/loans")
@AllArgsConstructor
@Validated
public class LoansController {

    private LoansService loansService;

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

}
