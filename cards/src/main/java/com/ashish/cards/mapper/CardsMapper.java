package com.ashish.cards.mapper;

import com.ashish.cards.dto.CardsDto;
import com.ashish.cards.entity.Cards;

public class CardsMapper {

    public static CardsDto mapToCardsDto(Cards card, CardsDto dto) {
        dto.setMobileNumber(card.getMobileNumber());
        dto.setCardNumber(card.getCardNumber());
        dto.setCardType(card.getCardType());
        dto.setTotalLimit(card.getTotalLimit());
        dto.setAmountUsed(card.getAmountUsed());
        dto.setAvailableAmount(card.getAvailableAmount());
        return dto;
    }
}
