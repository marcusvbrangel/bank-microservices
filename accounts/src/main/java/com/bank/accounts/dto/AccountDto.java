package com.bank.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record AccountDto(
        @NotEmpty(message = "Account number can not be null or empty")
        @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
        Long accountNumber,
        @NotEmpty(message = "Account type can not be null or empty")
        String accountType,
        @NotEmpty(message = "Brand address can not be null or empty")
        String branchAddress
) {
}
