package com.sg.bankaccount.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class AccountDomainTest {

    @Test
    public void doNothing() {
        
    }

    @Test
    public void shouldSaveMoneyWhenMakeADepositInAccount() {
        AmountDomain amount = AmountDomain.from(new BigDecimal(100));
        BalanceDomain balance = BalanceDomain.from(amount);
        AccountDomain accountDomain = AccountDomain.from(balance);
        AmountDomain newAmount = AmountDomain.from(new BigDecimal(50));
        accountDomain = accountDomain.deposit(newAmount);
        BalanceDomain expectedBalance = BalanceDomain.from(AmountDomain.from(new BigDecimal(150)));
        Assertions.assertEquals(expectedBalance, accountDomain.getBalance());
    }

}
