package com.sg.bankaccount.domain.event;

import lombok.Getter;

@Getter
public class DebitedAccountEvent implements AccountEvent {

    private OperationEvent operation;

    private DebitedAccountEvent(OperationEvent operation) {
        this.operation = operation;
    }

    public static DebitedAccountEvent from(OperationEvent operation) {
        return new DebitedAccountEvent(operation);
    }
}
