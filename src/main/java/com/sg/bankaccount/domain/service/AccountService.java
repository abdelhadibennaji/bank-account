package com.sg.bankaccount.domain.service;

import com.sg.bankaccount.domain.AccountDomain;
import com.sg.bankaccount.domain.AmountDomain;

public interface AccountService {

    AccountDomain deposit(String accountNumber, AmountDomain amountDomain) ;

    AccountDomain withdrawal(String accountNumber, AmountDomain amountDomain) ;

}
