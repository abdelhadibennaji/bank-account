package com.sg.bankaccount.domain.service;

import com.sg.bankaccount.domain.bankaccount.AccountDomain;
import com.sg.bankaccount.domain.bankaccount.AmountDomain;

public interface AccountService {

    AccountDomain deposit(String accountNumber, AmountDomain amountDomain) ;

    AccountDomain withdrawal(String accountNumber, AmountDomain amountDomain) ;

}
