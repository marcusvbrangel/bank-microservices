package com.bank.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(
        name = "Accounts Details",
        description = "Schema to hold cards details information"
)
public record CustomerAccountDto(

        @Schema(
                description = "Name of the customer",
                example = "Santos Dumont"
        )
        @NotEmpty(message = "Name of the customer")
        @Size(min = 5, max = 30, message = "The length of name should be between 5 and 30 ")
        String name,

        @Schema(
                description = "Email of the customer",
                example = "santos.dumont@gmail.com"
        )
        @NotEmpty(message = "Email can not be null or empty")
        @Email(message = "Email should be a valid value")
        String email,

        @Schema(
                description = "Mobile number of the customer",
                example = "5524785214"
        )
        @NotEmpty(message = "Mobile number can not be null or empty")
        @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
        String mobileNumber,

        @Schema(
                description = "Account number of the account",
                example = "1830572220"
        )
        @NotEmpty(message = "Account number can not be null or empty")
        @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                Long accountNumber,

        @Schema(
                description = "Account type of the account",
                example = "Savings"
        )
        @NotEmpty(message = "Account type of the account")
        String accountType,

        @Schema(
                description = "Account brand address of the account",
                example = "123 , Main Street, New York"
        )
        @NotEmpty(message = "Brand address of the account")
        String branchAddress

) {
}
