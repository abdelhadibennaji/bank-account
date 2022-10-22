package com.sg.bankaccount.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class AmountDomain {

    private BigDecimal value;

    private AmountDomain(BigDecimal value) {
        this.value = value;
    }

    public static AmountDomain from(BigDecimal value) {
        return new AmountDomain(value);
    }

    public AmountDomain Add(AmountDomain amountDomain) {
        return from(this.value.add(amountDomain.value));
    }

    public AmountDomain subtract(AmountDomain amountDomain) {
        return from(this.value.subtract(amountDomain.value));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AmountDomain amountDomain = (AmountDomain) o;

        return Objects.equals(value, amountDomain.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
