package com.bank.cards.controller;

import com.bank.cards.dto.CardDto;
import com.bank.cards.dto.ResponseErrorDto;
import com.bank.cards.dto.ResponseSuccessDto;
import com.bank.cards.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/cards")
@AllArgsConstructor
@Validated
@Tag(
    name = "CRUD REST API for Card in WolfBank",
    description = "CRUD REST API in WolfBank to CREATE / READ / UPDATE / DELETE card details"
)
public class CardController {

    private CardService cardService;

    @PostMapping
    @Operation(
        summary = "Create Card REST API",
        description = "REST API to create a new card inside WolfBank"
    )
    @ApiResponse(
        responseCode = "201",
        description = "HTTP Status CREATED"
    )
    public ResponseEntity<ResponseSuccessDto> createCard(@Valid @RequestBody CardDto cardDto) {

        cardService.createCard(cardDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseSuccessDto(HttpStatus.CREATED.toString(), "Card created with success"));

    }

    @GetMapping("/{mobileNumber}")
    @Operation(
        summary = "Fetch Card Details REST API",
        description = "REST API to fetch Card details based on a mobile number"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status OK"
    )
    public ResponseEntity<CardDto> fetchCardByMobileNumber(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @PathVariable String mobileNumber) {
        CardDto cardDto = cardService.fetchCardByMobileNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(cardDto);
    }

    @PutMapping
    @Operation(
        summary = "Update Card Details REST API",
        description = "REST API to update Card based on a mobile number"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status OK"
    )
    @ApiResponse(
        responseCode = "500",
        description = "HTTP Status Internal Server Error",
        content = @Content(
            schema = @Schema(implementation = ResponseErrorDto.class)
        )
    )
    public ResponseEntity<ResponseSuccessDto> updateCard(@Valid @RequestBody CardDto cardDto) {

        cardService.updateCard(cardDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseSuccessDto(HttpStatus.OK.toString(), "Card updated successfully"));

    }

    @DeleteMapping("/{mobileNumber}")
    @Operation(
        summary = "Delete Card Details REST API",
        description = "REST API to delete Card details based on a mobile number"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status OK"
    )
    @ApiResponse(
        responseCode = "500",
        description = "HTTP Status Internal Server Error"
    )
    public ResponseEntity<ResponseSuccessDto> deleteCard(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @PathVariable String mobileNumber) {

        boolean isDeleted = cardService.deleteCard(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseSuccessDto(HttpStatus.OK.toString(), "Card deleted successfully"));

    }

}

