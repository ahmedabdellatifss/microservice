package com.roaa.accounts.service.impl;


import com.roaa.accounts.constants.AccountsConstants;
import com.roaa.accounts.dto.AccountsDto;
import com.roaa.accounts.dto.CustomerDto;
import com.roaa.accounts.entity.Accounts;
import com.roaa.accounts.entity.Customer;
import com.roaa.accounts.exception.CustomerAlreadyExistsException;
import com.roaa.accounts.exception.ResourceNotFoundException;
import com.roaa.accounts.mapper.AccountsMapper;
import com.roaa.accounts.mapper.CustomerMapper;
import com.roaa.accounts.repository.AccountRepository;
import com.roaa.accounts.repository.CustomerRepository;
import com.roaa.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl  implements IAccountsService {


    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    /**
     * Service class to handle the customer related operations
     *
     * @param customerDto - CustomerDto Object
     *
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given MobileNumber " + customerDto.getMobileNumber());
        }

        Customer savedCustomer = customerRepository.save(customer);

        accountRepository.save(createNewAccount(savedCustomer));

    }



    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);

        return newAccount;
    }
    /**
     * Create a new account based on the customer object
     * @param mobileNumber - Tnput mobile number
     * @return the new account details
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "CustomerId", customer.getCustomerId().toString())
        );

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;

    }

/**
 * Updates the account details based on the provided CustomerDto.
 *
 * @param customerDto - CustomerDto containing the updated account details
 * @return true if the account is updated successfully, false otherwise
 */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto !=null ){
            Accounts accounts = accountRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    /**
     * Deletes the account associated with the given mobile number.
     *
     * @param mobileNumber - The mobile number associated with the account to be deleted.
     * @return true if the account is deleted successfully, false otherwise.
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}