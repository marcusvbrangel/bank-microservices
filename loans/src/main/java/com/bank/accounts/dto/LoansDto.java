package com.bank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(
    name = "Loan",
    description = "Schema to hold Loan information"
)
public record LoansDto(

    @Schema(
        description = "Mobile number referent to loan",
        example = "8952147863"
    )
    @NotEmpty(message = "Mobile Number can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits")
    String mobileNumber,

    @Schema(
        description = "Loan number",
        example = "360012698547"
    )
    @NotEmpty(message = "Loan Number can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{12})", message = "LoanNumber must be 12 digits")
    String loanNumber,

    @Schema(
        description = "Loan Type",
        example = "Credit"
    )
    @NotEmpty(message = "LoanType can not be a null or empty")
    String loanType,

    @Schema(
        description = "Total loan",
        example = "2100.00"
    )
    @Positive(message = "Total loan amount should be greater than zero")
    int totalLoan,

    @Schema(
        description = "Loan used",
        example = "900.00"
    )
    @PositiveOrZero(message = "Total loan amount paid should be equal or greater than zero")
    int amountPaid,

    @Schema(
        description = "Available loan",
        example = "1200.00"
    )
    @PositiveOrZero(message = "Total outstanding amount should be equal or greater than zero")
    int outstandingAmount

) {
}
