package com.sg.bankaccount.domain;

import java.math.BigDecimal;

public class AmountDomain {

    private BigDecimal value;

    private AmountDomain(BigDecimal value) {
        this.value = value;
    }

    public static AmountDomain from(BigDecimal value) {
        return new AmountDomain(value);
    }
}
