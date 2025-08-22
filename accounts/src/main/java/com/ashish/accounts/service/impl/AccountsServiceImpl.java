package com.ashish.accounts.service.impl;

import com.ashish.accounts.constants.AccountsConstants;
import com.ashish.accounts.dto.AccountsDto;
import com.ashish.accounts.dto.AccountsMsgDto;
import com.ashish.accounts.dto.CustomerDto;
import com.ashish.accounts.entity.Accounts;
import com.ashish.accounts.entity.Customer;
import com.ashish.accounts.exception.CustomerAlreadyExistsException;
import com.ashish.accounts.exception.ResourceNotFoundException;
import com.ashish.accounts.mapper.AccountsMapper;
import com.ashish.accounts.mapper.CustomersMapper;
import com.ashish.accounts.repository.AccountsRepository;
import com.ashish.accounts.repository.CustomerRepository;
import com.ashish.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private static final Logger log = LoggerFactory.getLogger(AccountsServiceImpl.class);

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private final StreamBridge streamBridge;

    /**
     *
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomersMapper.maptToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());

        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with mobile number "
                    + customer.getMobileNumber());
        }

        Customer savedCustomer = customerRepository.save(customer);
        Accounts savedAccount = accountsRepository.save(createNewAccount(savedCustomer));
        sendCommunication(savedAccount, savedCustomer);
    }

    private void sendCommunication(Accounts accounts, Customer customer) {
        var accountsMsgDto = new AccountsMsgDto(accounts.getAccountNumber(), customer.getName(), customer.getEmail(), customer.getMobileNumber());

        log.info("Sending Communication request for the details:{}", accountsMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", accountsMsgDto);
        log.info("Is the Communication request successfully triggered ?:{}", result);
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts accounts = new Accounts();
        accounts.setCustomerId(customer.getCustomerId());
        long randomNo = 1000000000L + new Random().nextInt(90000000);

        accounts.setAccountNumber(randomNo);
        accounts.setAccountType(AccountsConstants.SAVINGS);
        accounts.setBranchAddress(AccountsConstants.ADDRESS);

        return accounts;
    }

    /**
     *
     * @param mobileNumber - mobile number of customer
     * @return Accounts Details based on a given mobileNumber.
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "Customer Id", customer.getCustomerId())
        );

        CustomerDto customerDto = CustomersMapper.maptToCustomerDto(customer, new CustomerDto());
        customerDto.setAccounts(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;

        AccountsDto accountsDto = customerDto.getAccounts();

        if (accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "accountNumber", accountsDto.getAccountNumber())
            );

            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long id = accounts.getCustomerId();
            Customer customer = customerRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "Customer Id", id)
            );

            CustomersMapper.maptToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }

        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

        return true;
    }

    @Override
    public boolean updateCommunicationStatus(Long accountNumber) {
        boolean isUpdated = false;
        if(accountNumber != null) {
            Accounts accounts = accountsRepository.findById(accountNumber).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "accountNumber", accountNumber.toString())
            );
            accounts.setCommunicationSw(true);
            accountsRepository.save(accounts);
            isUpdated = true;
        }
        return isUpdated;
    }


}
