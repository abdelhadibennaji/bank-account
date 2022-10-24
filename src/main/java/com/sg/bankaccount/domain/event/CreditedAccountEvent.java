package com.sg.bankaccount.domain.event;

import lombok.Getter;

@Getter
public class CreditedAccountEvent implements AccountEvent {

    private OperationEvent operation;

    private CreditedAccountEvent(OperationEvent operation) {
        this.operation = operation;
    }

    public static CreditedAccountEvent from(OperationEvent operation) {
        return new CreditedAccountEvent(operation);
    }

}
