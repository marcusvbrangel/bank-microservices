package com.bank.cards.controller;

import com.bank.cards.dto.CardDto;
import com.bank.cards.dto.ResponseSuccessDto;
import com.bank.cards.service.CardService;
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
public class CardController {

    private CardService cardService;

    @PostMapping
    public ResponseEntity<ResponseSuccessDto> createCard(@Valid @RequestBody CardDto cardDto) {

        cardService.createCard(cardDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseSuccessDto(HttpStatus.CREATED.toString(), "Card created with success"));

    }

    @GetMapping("/{mobileNumber}")
    public ResponseEntity<CardDto> fetchCardByMobileNumber(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @PathVariable String mobileNumber) {
        CardDto cardDto = cardService.fetchCardByMobileNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(cardDto);
    }

    @PutMapping
    public ResponseEntity<ResponseSuccessDto> updateCard(@Valid @RequestBody CardDto cardDto) {

        cardService.updateCard(cardDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseSuccessDto(HttpStatus.OK.toString(), "Card updated successfully"));

    }

    @DeleteMapping("/{mobileNumber}")
    public ResponseEntity<ResponseSuccessDto> deleteCard(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @PathVariable String mobileNumber) {

        boolean isDeleted = cardService.deleteCard(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseSuccessDto(HttpStatus.OK.toString(), "Card deleted successfully"));

    }

}
