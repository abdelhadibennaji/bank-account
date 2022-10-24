package com.sg.bankaccount.domain.bankaccount;

import com.sg.bankaccount.domain.exception.InsufficientBalanceException;
import com.sg.bankaccount.domain.exception.NegativeBalanceException;

public class AccountDomain {

    private BalanceDomain balanceDomain;

    private AccountDomain(BalanceDomain balanceDomain) {
        this.balanceDomain = balanceDomain;
    }

    public static AccountDomain from(BalanceDomain balanceDomain) {
        return new AccountDomain(balanceDomain);
    }

    public AccountDomain deposit(AmountDomain amountDomain) {
        BalanceDomain balanceDomain = this.balanceDomain.addAmount(amountDomain);
        return from(balanceDomain);
    }

    public AccountDomain withdrawal(AmountDomain amountDomain) {
        try {
            BalanceDomain balanceDomain = this.balanceDomain.subtract(amountDomain);
            return from(balanceDomain);
        } catch (NegativeBalanceException e) {
            throw new InsufficientBalanceException("Balance insufficient for your operation");
        }
    }

    public BalanceDomain getBalance() {
        return balanceDomain;
    }
}
