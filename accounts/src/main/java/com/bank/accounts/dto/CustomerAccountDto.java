package com.bank.accounts.dto;

public record CustomerAccountDto(String name,
                                 String email,
                                 String mobileNumber,
                                 Long accountNumber,
                                 String accountType,
                                 String branchAddress) {
}
