package com.bank.accounts.controller;

import com.bank.accounts.dto.*;
import com.bank.accounts.service.AccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "CRUD REST APIs for Accounts in WolfBank",
    description = "CRUD REST APIs in WolfBank to CREATE, UPDATE, FETCH AND DELETE account details"
)
@RestController
@RequestMapping(value = "/api/v1/accounts")
@Validated
public class AccountsController {

    private final AccountsService accountsService;

    private final Environment environment;

    private final AccountsContactInfoDto accountsContactInfoDto;

    @Value("${build.version}")
    private String buildVersion;

    public AccountsController(AccountsService accountsService, Environment environment,
                              AccountsContactInfoDto accountsContactInfoDto) {
        this.accountsService = accountsService;
        this.environment = environment;
        this.accountsContactInfoDto = accountsContactInfoDto;
    }

    @Operation(
        summary = "Create Account REST API",
        description = "REST API to create new Customer & Account inside WolfBank"
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
    public ResponseEntity<ResponseSuccessDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {

        accountsService.createAccount(customerDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @Operation(
        summary = "Fetch Account Details REST API",
        description = "REST API to fetch Customer & Account details based on a mobile number"
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
    public ResponseEntity<CustomerAccountDto> fetchAccountByMobileNumber(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @PathVariable String mobileNumber) {
        CustomerAccountDto customerAccountDto = accountsService.fetchAccountByMobileNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerAccountDto);
    }

    @Operation(
        summary = "Update Account Details REST API",
        description = "REST API to Update Customer & Account details based on a mobile number"
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
    public ResponseEntity<ResponseSuccessDto> MobileNumber(
            @NotEmpty(message = "Mobile number can not be null or empty")
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @PathVariable String mobileNumber,
            @Valid @RequestBody CustomerAccountDto customerAccountDto) {

        accountsService.updateAccountByMobileNumber(mobileNumber, customerAccountDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @Operation(
        summary = "Delete Account Details REST API",
        description = "REST API to Delete Customer & Account details based on a mobile number"
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
    public ResponseEntity<ResponseSuccessDto> deleteAccountByMobileNumber(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @PathVariable String mobileNumber) {

        accountsService.deleteAccountByMobileNumber(mobileNumber);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

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
