package com.bank.accounts.service;

import com.bank.accounts.dto.CardDto;
import com.bank.accounts.entity.Card;
import com.bank.accounts.exception.CardAlreadyExistsException;
import com.bank.accounts.exception.ResourceNotFoundException;
import com.bank.accounts.repository.CardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CardService {

    private CardRepository cardRepository;

    @Transactional
    public void createCard(CardDto cardDto) {

        Optional<Card> cardExisting = cardRepository.findByMobileNumber(cardDto.mobileNumber());

        if (cardExisting.isPresent()) {
            throw new CardAlreadyExistsException("Card with mobile number "
                    + cardDto.mobileNumber() + " already exists");
        }

        Card card = new Card();
        card.setMobileNumber(cardDto.mobileNumber());
        card.setCardNumber(cardDto.cardNumber());
        card.setCardType(cardDto.cardType());
        card.setTotalLimit(cardDto.totalLimit());
        card.setAmountUsed(cardDto.amountUsed());
        card.setAvailableAmount(cardDto.availableAmount());

        cardRepository.save(card);

    }

    public CardDto fetchCardByMobileNumber(String mobileNumber) {

        Card card =  cardRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Wasn't possible to find a card with the mobile number " + mobileNumber));

        return new CardDto(
                card.getMobileNumber(),
                card.getCardNumber(),
                card.getCardType(),
                card.getTotalLimit(),
                card.getAmountUsed(),
                card.getAvailableAmount()
        );

    }

    @Transactional
    public boolean updateCard(final CardDto cardDto) {

        Card card = cardRepository.findByMobileNumber(cardDto.mobileNumber())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Wasn't possible to find a card with the account number " + cardDto.mobileNumber()));

        card.setMobileNumber(cardDto.mobileNumber());
        card.setCardNumber(cardDto.cardNumber());
        card.setCardType(cardDto.cardType());
        card.setTotalLimit(cardDto.totalLimit());
        card.setAmountUsed(cardDto.amountUsed());
        card.setAvailableAmount(cardDto.availableAmount());

        cardRepository.save(card);

        return true;

    }

    @Transactional
    public boolean deleteCard(String mobileNumber) {

        Card card = cardRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Wasn't possible to find a card with the mobile number " + mobileNumber));

        cardRepository.delete(card);

        return true;

    }

}
