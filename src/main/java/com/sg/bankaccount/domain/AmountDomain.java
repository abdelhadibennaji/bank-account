package com.sg.bankaccount.domain;

import com.sg.bankaccount.domain.exception.NegativeAmountException;
import com.sg.bankaccount.domain.exception.NegativeBalanceException;

import java.math.BigDecimal;
import java.util.Objects;

public class AmountDomain {

    private BigDecimal value;

    private AmountDomain(BigDecimal value) {
        if(value.doubleValue()<0) throw new NegativeAmountException("Amount can not be negative");
        this.value = value;
    }

    public static AmountDomain from(BigDecimal value) {
        return new AmountDomain(value);
    }

    public AmountDomain Add(AmountDomain amountDomain) {
        return from(this.value.add(amountDomain.value));
    }

    public AmountDomain subtract(AmountDomain amountDomain) {
        try {
            return from(this.value.subtract(amountDomain.value));
        } catch (NegativeAmountException e) {
            throw new NegativeBalanceException("Balance can not be negative");
        }
    }

    public BigDecimal getValue() {
        return value;
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
