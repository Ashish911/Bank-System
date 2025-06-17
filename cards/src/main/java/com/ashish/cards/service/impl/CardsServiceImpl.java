package com.ashish.cards.service.impl;

import com.ashish.cards.constants.CardsConstants;
import com.ashish.cards.dto.CardsDto;
import com.ashish.cards.entity.Cards;
import com.ashish.cards.exception.CardAlreadyExistsException;
import com.ashish.cards.exception.ResourceNotFoundException;
import com.ashish.cards.mapper.CardsMapper;
import com.ashish.cards.respository.CardsRepository;
import com.ashish.cards.service.ICardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.smartcardio.Card;
import java.util.Optional;
import java.util.Random;

@Service
public class CardsServiceImpl implements ICardsService {

    @Autowired
    private CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);

        if (optionalCards.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with mobile number "
                    + mobileNumber);
        }

        cardsRepository.save(createNewCard(mobileNumber));
    }

    private Cards createNewCard(String mobileNumber) {
        Cards cards = new Cards();
        cards.setMobileNumber(mobileNumber);

        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        cards.setCardNumber(Long.toString(randomCardNumber));
        cards.setCardType(CardsConstants.CREDIT_CARD);
        cards.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        cards.setAmountUsed(0);
        cards.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);

        return cards;
    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards card = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );

        CardsDto dto = CardsMapper.mapToCardsDto(card, new CardsDto());

        return dto;
    }

    @Override
    public boolean updateCard(CardsDto dto) {
        boolean isUpdated = false;

        Cards cards = cardsRepository.findByCardNumber(dto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", dto.getCardNumber())
        );

        CardsMapper.mapToCards(dto, cards);
        cardsRepository.save(cards);
        isUpdated = true;

        return isUpdated;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        cardsRepository.deleteById(cards.getCardId());

        return true;
    }
}
