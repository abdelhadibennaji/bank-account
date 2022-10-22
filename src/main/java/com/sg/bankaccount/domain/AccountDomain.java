package com.sg.bankaccount.domain;

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
        BalanceDomain balanceDomain = this.balanceDomain.subtract(amountDomain);
        return from(balanceDomain);
    }

    public BalanceDomain getBalance() {
        return balanceDomain;
    }
}
