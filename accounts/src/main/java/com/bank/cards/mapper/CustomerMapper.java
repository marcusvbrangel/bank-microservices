package com.bank.cards.mapper;

import com.bank.cards.dto.CustomerDto;
import com.bank.cards.entity.Customer;

public class CustomerMapper {

    public static CustomerDto mapToCustomerDto(Customer customer) {
        return new CustomerDto(customer.getName(), customer.getEmail(), customer.getMobileNumber());
    }

    public static Customer mapToCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.name());
        customer.setEmail(customerDto.email());
        customer.setMobileNumber(customerDto.mobileNumber());
        return customer;
    }

}
