package com.sg.bankaccount.domain.service;

import com.sg.bankaccount.domain.bankaccount.BalanceDomain;
import com.sg.bankaccount.domain.event.CreditedAccountEvent;
import com.sg.bankaccount.domain.event.DebitedAccountEvent;

public interface AccountPort {

    BalanceDomain findBalanceByAccountNumber(String accountNumber);

    void publishEvent(CreditedAccountEvent event) ;

    void publishEvent(DebitedAccountEvent event);
}
