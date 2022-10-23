package com.sg.bankaccount.infrastructure.exception;

public class TechnicalException extends RuntimeException {

    public TechnicalException(String message) {
        super("Technical exception : "+message);
    }
}
