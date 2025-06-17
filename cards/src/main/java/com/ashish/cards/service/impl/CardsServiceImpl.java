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
    public void createCard(String mobileNumber) {
        CardsDto cardsDto = new CardsDto();

        cardsDto.setMobileNumber(mobileNumber);
        cardsDto.setCardNumber("1234567890123456");
        cardsDto.setCardType("Credit");
        cardsDto.setTotalLimit(10000L);
        cardsDto.setAmountUsed(0L);
        cardsDto.setAvailableAmount(10000L);

        Cards cards = CardsMapper.mapToCards(cardsDto, new Cards());

        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(cardsDto.getMobileNumber());

        if (optionalCards.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with mobile number "
                    + cardsDto.getMobileNumber());
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
        boolean isUpdated = false;

        Cards cards = cardsRepository.findByMobileNumber(dto.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", dto.getMobileNumber())
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
