package com.sg.bankaccount.domain;

public class AccountDomain {

    private BalanceDomain balanceDomain;

    private AccountDomain(BalanceDomain balanceDomain) {
        this.balanceDomain = balanceDomain;
    }

    public static AccountDomain from(BalanceDomain balanceDomain) {
        return new AccountDomain(balanceDomain);
    }

    public AccountDomain deposit(AmountDomain newAmount) {
        return null;
    }

    public BalanceDomain getBalance() {
        return balanceDomain;
    }
}
