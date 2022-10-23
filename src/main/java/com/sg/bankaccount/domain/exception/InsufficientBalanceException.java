package com.sg.bankaccount.domain.exception;

public class InsufficientBalanceException extends RuntimeException{

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
