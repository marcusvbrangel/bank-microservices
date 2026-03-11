package com.bank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(
    name = "Card",
    description = "Schema to hold Card information"
)
public record CardDto(

    @Schema(
        description = "Mobile number referent to card",
        example = "8952147863"
    )
    @NotEmpty(message = "Mobile Number can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits")
    String mobileNumber,

    @Schema(
        description = "Card number",
        example = "360012698547"
    )
    @NotEmpty(message = "Card Number can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{12})",message = "CardNumber must be 12 digits")
    String cardNumber,

    @Schema(
        description = "Card Type",
        example = "Credit"
    )
    @NotEmpty(message = "CardType can not be a null or empty")
    String cardType,

    @Schema(
        description = "Total limit",
        example = "2100.00"
    )
    @Positive(message = "Total card limit should be greater than zero")
    int totalLimit,

    @Schema(
        description = "Amount used",
        example = "900.00"
    )
    @PositiveOrZero(message = "Total amount used should be equal or greater than zero")
    int amountUsed,

    @Schema(
        description = "Available amount",
        example = "1200.00"
    )
    @PositiveOrZero(message = "Total available amount should be equal or greater than zero")
    int availableAmount

) {

}
