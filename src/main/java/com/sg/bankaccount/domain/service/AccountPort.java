package com.sg.bankaccount.domain.service;

import com.sg.bankaccount.domain.BalanceDomain;
import com.sg.bankaccount.domain.event.CreditedAccountEvent;
import com.sg.bankaccount.domain.event.DebitedAccountEvent;

public interface AccountPort {

    BalanceDomain findBalanceByAccountNumber (String accountNumber);

    void saveEvent(CreditedAccountEvent event) ;

    void saveEvent(DebitedAccountEvent event);

}
