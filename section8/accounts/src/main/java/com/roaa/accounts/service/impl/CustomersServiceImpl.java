package com.roaa.accounts.service.impl;

import com.roaa.accounts.dto.AccountsDto;
import com.roaa.accounts.dto.CardsDto;
import com.roaa.accounts.dto.CustomerDetailsDto;
import com.roaa.accounts.dto.LoansDto;
import com.roaa.accounts.entity.Accounts;
import com.roaa.accounts.entity.Customer;
import com.roaa.accounts.exception.ResourceNotFoundException;
import com.roaa.accounts.mapper.AccountsMapper;
import com.roaa.accounts.mapper.CustomerMapper;
import com.roaa.accounts.repository.AccountRepository;
import com.roaa.accounts.repository.CustomerRepository;
import com.roaa.accounts.service.ICustomersService;
import com.roaa.accounts.service.client.CardsFeignClient;
import com.roaa.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "CustomerId", customer.getCustomerId().toString())
        );
        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
