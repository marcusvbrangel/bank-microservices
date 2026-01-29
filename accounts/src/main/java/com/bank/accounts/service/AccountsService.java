package com.bank.accounts.service;

import com.bank.accounts.constants.AccountsConstants;
import com.bank.accounts.dto.AccountDto;
import com.bank.accounts.dto.CustomerAccountDto;
import com.bank.accounts.dto.CustomerDto;
import com.bank.accounts.entity.Accounts;
import com.bank.accounts.entity.Customer;
import com.bank.accounts.exception.CustomerAlreadyExistsException;
import com.bank.accounts.exception.ResourceNotFoundException;
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

    public boolean updateAccount(CustomerAccountDto customerAccountDto) {

        Accounts accounts = accountsRepository.findById(customerAccountDto.accountNumber())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Wasn't possible to find account with the account number " + customerAccountDto.accountNumber()));

        accounts.setAccountNumber( customerAccountDto.accountNumber());
        accounts.setBranchAddress(customerAccountDto.branchAddress());
        accounts.setAccountType(customerAccountDto.accountType());
        accounts.setUpdatedAt(LocalDateTime.now());
        accounts.setUpdatedBy("anonymous");

        accounts =  accountsRepository.save(accounts);

        Long customerId = accounts.getCustomerId();

        Customer customer = customerRepository.findById(accounts.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Wasn't possible to find a customer with the customer id " + customerId));

        customer.setName(customerAccountDto.name());
        customer.setEmail(customerAccountDto.email());
        customer.setMobileNumber(customerAccountDto.mobileNumber());
        customer.setUpdatedAt(LocalDateTime.now());
        customer.setUpdatedBy("anonymous");

        customerRepository.save(customer);

        return true;

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
