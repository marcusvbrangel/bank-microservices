package com.bank.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerAccountDto(
     @NotEmpty(message = "Name can not be null or empty")
     @Size(min = 5, max = 30, message = "The length of name should be between 5 and 30 ")
     String name,
     @NotEmpty(message = "Email can not be null or empty")
     @Email(message = "Email should be a valid value")
     String email,
     @NotEmpty(message = "Mobile number can not be null or empty")
     @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
     String mobileNumber,
     @NotNull(message = "Account number can not be null")
     @Min(value = 1000000000L, message = "Account number must be 10 digits")
     @Max(value = 9999999999L, message = "Account number must be 10 digits")
     Long accountNumber,
     @NotEmpty(message = "Account type can not be null or empty")
     String accountType,
     @NotEmpty(message = "Brand address can not be null or empty")
     String branchAddress
) {
}
