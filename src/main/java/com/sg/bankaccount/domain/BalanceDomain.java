package com.sg.bankaccount.domain;

public class BalanceDomain {

    private AmountDomain amountDomain;

    private BalanceDomain(AmountDomain amountDomain) {
        this.amountDomain= amountDomain;
    }

    public static BalanceDomain from(AmountDomain amount) {
        return new BalanceDomain(amount);
    }
}
