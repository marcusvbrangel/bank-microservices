package com.bank.accounts.service;

import com.bank.accounts.constants.AccountsConstants;
import com.bank.accounts.dto.AccountDto;
import com.bank.accounts.dto.CustomerAccountDto;
import com.bank.accounts.dto.CustomerDto;
import com.bank.accounts.entity.Accounts;
import com.bank.accounts.entity.Customer;
import com.bank.accounts.exception.CustomerAlreadyExistsException;
import com.bank.accounts.exception.ResourceNotFoundException;
import com.bank.accounts.mapper.AccountsMapper;
import com.bank.accounts.mapper.CustomerMapper;
import com.bank.accounts.repository.AccountsRepository;
import com.bank.accounts.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto);
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.mobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer with mobile number "
                    + customerDto.mobileNumber() + " already exists");
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("anonymous");
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    public CustomerAccountDto fetchAccountByMobileNumber(String mobileNumber) {

        Customer customer =  customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Wasn't possible to find a customer with the mobile number " + mobileNumber));

        return accountsRepository.findByCustomerId(customer.getCustomerId())
                .map((account) -> {
                    return new CustomerAccountDto(
                            customer.getName(),
                            customer.getEmail(),
                            customer.getMobileNumber(),
                            account.getAccountNumber(),
                            account.getAccountType(),
                            account.getBranchAddress() );
                })
                .orElseThrow(() ->
                        new ResourceNotFoundException("Wasn't possible to find a account with the customer id "
                                + customer.getCustomerId()));

    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("anonymous");
        return newAccount;
    }

}
