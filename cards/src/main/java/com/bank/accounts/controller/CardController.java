package com.bank.accounts.controller;

import com.bank.accounts.dto.CardDto;
import com.bank.accounts.dto.CardsContactInfoDto;
import com.bank.accounts.dto.ResponseSuccessDto;
import com.bank.accounts.service.CardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/accounts")
@Validated
public class CardController {

    private CardService cardService;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private CardsContactInfoDto cardsContactInfoDto;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

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

    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    @GetMapping("/contact-info")
    public ResponseEntity<CardsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardsContactInfoDto);
    }

}
