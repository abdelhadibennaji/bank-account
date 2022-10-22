package com.sg.bankaccount.domain;

import java.util.Objects;

public class BalanceDomain {

    private AmountDomain amountDomain;

    private BalanceDomain(AmountDomain amountDomain) {
        this.amountDomain= amountDomain;
    }

    public static BalanceDomain from(AmountDomain amount) {
        return new BalanceDomain(amount);
    }

    public BalanceDomain addAmount(AmountDomain amountDomain) {
        AmountDomain amountDomain1 = this.amountDomain.Add(amountDomain);
        return from(amountDomain1);
    }

    public BalanceDomain subtract(AmountDomain amountDomain) {
        AmountDomain amountDomain1 = this.amountDomain.subtract(amountDomain);
        return from(amountDomain1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BalanceDomain balanceDomain = (BalanceDomain) o;

        return Objects.equals(amountDomain, balanceDomain.amountDomain);
    }

    @Override
    public int hashCode() {
        return amountDomain != null ? amountDomain.hashCode() : 0;
    }
}
