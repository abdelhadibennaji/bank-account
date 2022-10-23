package com.sg.bankaccount.domain.exception;

public class NegativeBalanceException extends RuntimeException {

    public NegativeBalanceException(String message) {
        super(message);
    }
}
