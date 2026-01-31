package com.bank.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
public record AccountDto(

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
                example = "123 Main Street, New York"
        )
        @NotEmpty(message = "Brand address of the account")
        String branchAddress
) {
}
