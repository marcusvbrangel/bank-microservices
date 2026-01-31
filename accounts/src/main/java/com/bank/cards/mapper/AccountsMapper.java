package com.bank.cards.mapper;

import com.bank.cards.dto.AccountDto;
import com.bank.cards.entity.Accounts;

public class AccountsMapper {

    public static AccountDto mapToAccountDto(Accounts accounts) {
        return new AccountDto(accounts.getAccountNumber(),
                                    accounts.getAccountType(),
                                    accounts.getBranchAddress());
    }

    public static Accounts mapToAccounts(AccountDto accountDto) {
        Accounts accounts = new Accounts();
        accounts.setAccountNumber(accountDto.accountNumber());
        accounts.setAccountType(accountDto.accountType());
        accounts.setBranchAddress(accountDto.branchAddress());
        return accounts;
    }

}
