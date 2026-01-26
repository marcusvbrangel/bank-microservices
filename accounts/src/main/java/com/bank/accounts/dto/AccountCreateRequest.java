package com.bank.accounts.dto;

public record AccountCreateRequest(Long accountNumber, String accountType, String branchAddress) {
}
