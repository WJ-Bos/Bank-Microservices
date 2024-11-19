package com.wjbos.accounts.mapper;

import com.wjbos.accounts.dto.AccountsDto;
import com.wjbos.accounts.entity.Accounts;

public class AccountsMapper {


    /**
     *
     * @param accounts
     * @param accountsDto
     * @return
     */
    public static AccountsDto mapToAccountsDto(Accounts accounts, AccountsDto accountsDto) {
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }

    /**
     * @param accountsDto
     * @param accounts
     * @return
     */
    public static Accounts mapToAccounts(AccountsDto accountsDto, Accounts accounts) {
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }
}