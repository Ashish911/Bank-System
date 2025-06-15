package com.ashish.cards.service;

import com.ashish.cards.dto.CardsDto;

public interface ICardsService {

    /**
     * @param dto - CardsDto Object
     */
    void createCard(CardsDto dto);

    /**
     * @param mobileNumber - mobile number of customer.
     * @return Card Details based on a given mobileNumber.
     */
    CardsDto fetchCard(String mobileNumber);

    /**
     * @param dto - CustomerDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    boolean updateCard(CardsDto dto);

    /**
     * @param mobileNumber - mobile number of customer.
     * @return boolean indicating if the deletion of card is successful or not
     */
    boolean deleteCard(String mobileNumber);
}
