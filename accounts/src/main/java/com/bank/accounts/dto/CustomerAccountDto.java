package com.bank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(
        name = "CustomerAccount",
        description = "Schema to hold Customer and account information"
)
public record CustomerAccountDto(

        @Schema(
                description = "Name of the customer",
                example = "Jean Morales"
        )
        @NotEmpty(message = "Name can not be null or empty")
        @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30 ")
        String name,

        @Schema(
                description = "Email address of the customer",
                example = "jean@gmail.com"
        )
        @NotEmpty(message = "Email address can not be null or empty")
        @Email(message = "Email address should be a valid value")
        String email,

        @Schema(
                description = "Mobile number of the customer",
                example = "9345432123"
        )
        String mobileNumber,

        @Schema(
                description = "Account Number of Wolf Bank account",
                example = "3454433243"
        )
        @NotEmpty(message = "Account number can not be a null or empty")
        @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
        String accountNumber,

        @Schema(
                description = "Account type of Wolf Bank account",
                example = "Savings"
        )
        @NotEmpty(message = "Account type can not be a null or empty")
        String accountType,

        @Schema(
                description = "Wolf Bank branch address",
                example = "123 NewYork"
        )
        @NotEmpty(message = "Branch address can not be a null or empty")
        @NotEmpty(message = "Brand address of the account")
        String branchAddress

) {
}
