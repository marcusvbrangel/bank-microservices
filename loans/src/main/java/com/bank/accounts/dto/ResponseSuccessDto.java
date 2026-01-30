package com.bank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "Success Response",
        description = "Schema to hold successful response information"
)
public record ResponseSuccessDto(

        @Schema(
                description = "Status code in the response"
        )
        String statusCode,

        @Schema(
                description = "Status message in the response"
        )
        String statusMessage
) {
}
