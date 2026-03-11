package com.bank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(
    name = "Customer",
    description = "Schema to hold Customer and account information"
)
public record CustomerDto(

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
        @NotEmpty(message = "Mobile number can not be null or empty")
        @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
        String mobileNumber

) {
}
