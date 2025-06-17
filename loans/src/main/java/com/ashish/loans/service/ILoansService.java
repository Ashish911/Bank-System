package com.ashish.loans.service;

import com.ashish.loans.dto.LoansDto;

public interface ILoansService {

    /**
     * @param mobileNumber - String number.
     */
    void createLoan(String mobileNumber);

    /**
     * @param mobileNumber - mobile number of customer.
     * @return Card Details based on a given mobileNumber.
     */
    LoansDto fetchLoan(String mobileNumber);

    /**
     * @param dto - CustomerDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    boolean updateLoan(LoansDto dto);

    /**
     * @param mobileNumber - mobile number of customer.
     * @return boolean indicating if the deletion of card is successful or not
     */
    boolean deleteLoan(String mobileNumber);

}
