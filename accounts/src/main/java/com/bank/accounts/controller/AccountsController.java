package com.bank.accounts.controller;

import com.bank.accounts.constants.AccountsConstants;
import com.bank.accounts.dto.AccountDto;
import com.bank.accounts.dto.ResponseSuccess;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/accounts",
                produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
public class AccountsController {

    @PostMapping
    public ResponseEntity<ResponseSuccess> create(@RequestBody AccountDto accountCreateRequest) {



        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseSuccess(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));

    }

}























