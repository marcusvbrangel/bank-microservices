package com.bank.accounts.controller;

import com.bank.accounts.dto.CardDto;
import com.bank.accounts.dto.ResponseErrorDto;
import com.bank.accounts.dto.ResponseSuccessDto;
import com.bank.accounts.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "CRUD REST APIs for Cards in WolfBank",
    description = "CRUD REST APIs in WolfBank to CREATE, UPDATE, FETCH AND DELETE card details"
)
@RestController
@RequestMapping(value = "/api/v1/cards")
@Validated
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @Operation(
        summary = "Create Card REST API",
        description = "REST API to create new Card inside WolfBank"
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
    public ResponseEntity<ResponseSuccessDto> createCard(@Valid @RequestBody CardDto cardDto) {

        cardService.createCard(cardDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @Operation(
        summary = "Fetch Card Details REST API",
        description = "REST API to fetch Card details based on a mobile number"
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
    public ResponseEntity<CardDto> fetchCardByMobileNumber(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @PathVariable String mobileNumber) {
        CardDto cardDto = cardService.fetchCardByMobileNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardDto);
    }

    @Operation(
        summary = "Update Card Details REST API",
        description = "REST API to Update Card details based on a mobile number"
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
    public ResponseEntity<ResponseSuccessDto> updateCardByMobileNumber(
                                                                @Pattern(regexp = "(^$|[0-9]{10})",
                                                                         message = "Mobile number must be 10 digits")
                                                                @PathVariable String mobileNumber,
                                                                @Valid @RequestBody CardDto cardDto) {

        cardService.updateCardByMobileNumber(mobileNumber, cardDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @Operation(
        summary = "Delete Card Details REST API",
        description = "REST API to Delete Card details based on a mobile number"
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
    public ResponseEntity<ResponseSuccessDto> deleteCardByMobileNumber(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @PathVariable String mobileNumber) {

        cardService.deleteCardByMobileNumber(mobileNumber);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
