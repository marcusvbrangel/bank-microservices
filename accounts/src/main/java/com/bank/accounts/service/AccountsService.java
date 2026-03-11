package com.bank.accounts.service;

import com.bank.accounts.constants.AccountsConstants;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Transactional
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto);
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.mobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer with mobile number "
                    + customerDto.mobileNumber() + " already exists");
        }

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
                            String.valueOf(account.getAccountNumber()),
                            account.getAccountType(),
                            account.getBranchAddress() );
                })
                .orElseThrow(() ->
                        new ResourceNotFoundException("Wasn't possible to find a account with the customer id "
                                + customer.getCustomerId()));

    }

    @Transactional
    public void updateAccountByAccountNumber(String accountNumber, CustomerAccountDto customerAccountDto) {

        Accounts accounts = accountsRepository.findById(Long.valueOf(accountNumber))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Wasn't possible to find account with the account number " + accountNumber));

        accounts.setBranchAddress(customerAccountDto.branchAddress());
        accounts.setAccountType(customerAccountDto.accountType());

        accounts =  accountsRepository.save(accounts);

        Long customerId = accounts.getCustomerId();

        Customer customer = customerRepository.findById(accounts.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Wasn't possible to find a customer with the customer id " + customerId));

        customer.setName(customerAccountDto.name());
        customer.setMobileNumber(customerAccountDto.mobileNumber());
        customer.setEmail(customerAccountDto.email());
        customer.setMobileNumber(customerAccountDto.mobileNumber());

        customerRepository.save(customer);

    }

    @Transactional
    public void deleteAccountByMobileNumber(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Wasn't possible to find a customer with the mobile number " + mobileNumber));

        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

    }


    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);

        return newAccount;
    }

}
