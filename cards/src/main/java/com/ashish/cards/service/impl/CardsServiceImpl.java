package com.ashish.cards.service.impl;

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

@Service
public class CardsServiceImpl implements ICardsService {

    @Autowired
    private CardsRepository cardsRepository;

    @Override
    public void createCard(CardsDto dto) {
        Cards cards = CardsMapper.mapToCards(dto, new Cards());
        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(dto.getMobileNumber());

        if (optionalCards.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with mobile number "
                    + cards.getMobileNumber());
        }

        cardsRepository.save(cards);
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
        return false;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        return false;
    }
}
