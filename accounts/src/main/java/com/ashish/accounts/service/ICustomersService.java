package com.ashish.accounts.service;

import com.ashish.accounts.dto.CustomerDetailsDto;

public interface ICustomersService {

    /**
     * This method is used to fetch the customer details based on the mobile number.
     *
     * @param mobileNumber - the mobile number of the customer
     * @return the customer details
     */
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);
}
