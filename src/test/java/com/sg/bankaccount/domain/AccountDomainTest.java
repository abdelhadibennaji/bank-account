package com.sg.bankaccount.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(expectedBalance, accountDomain.getBalance());
    }

    @Test
    public void shouldMakeWithdrawalWhenWhenRetrieveMoneyAndThereIsEnoughMoney() {
        AmountDomain amountDomain = AmountDomain.from(new BigDecimal(100));
        BalanceDomain balanceDomain = BalanceDomain.from(amountDomain);
        AccountDomain accountDomain = AccountDomain.from(balanceDomain);
        AmountDomain newAmountDomain = AmountDomain.from(new BigDecimal(50));
        accountDomain = accountDomain.withdrawal(newAmountDomain);
        BalanceDomain expectedBalance = BalanceDomain.from(AmountDomain.from(new BigDecimal(50)));
        assertEquals(expectedBalance, accountDomain.getBalance());
    }

}
