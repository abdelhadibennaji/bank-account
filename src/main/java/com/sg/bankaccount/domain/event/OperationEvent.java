package com.sg.bankaccount.domain.event;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class OperationEvent {

    private String accountNumber;
    private BigDecimal amount;
    private BigDecimal balance;
    private LocalDateTime operationDate;

    private OperationEvent(String accountNumber, BigDecimal amount, BigDecimal balance, LocalDateTime operationDate) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.balance = balance;
        this.operationDate = operationDate;
    }

    public static OperationEvent from(String accountNumber, BigDecimal amount, BigDecimal balance, LocalDateTime operationDate) {
        return new OperationEvent(accountNumber, amount, balance, operationDate);
    }
}
