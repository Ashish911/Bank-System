package com.ashish.accounts.service.impl;

import com.ashish.accounts.dto.AccountsDto;
import com.ashish.accounts.dto.CardsDto;
import com.ashish.accounts.dto.CustomerDetailsDto;
import com.ashish.accounts.dto.LoansDto;
import com.ashish.accounts.entity.Accounts;
import com.ashish.accounts.entity.Customer;
import com.ashish.accounts.exception.ResourceNotFoundException;
import com.ashish.accounts.mapper.AccountsMapper;
import com.ashish.accounts.mapper.CustomersMapper;
import com.ashish.accounts.repository.AccountsRepository;
import com.ashish.accounts.repository.CustomerRepository;
import com.ashish.accounts.service.ICustomersService;
import com.ashish.accounts.service.client.CardsFeignClient;
import com.ashish.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId())
        );

        CustomerDetailsDto customerDetailsDto = CustomersMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoansDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardsDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
