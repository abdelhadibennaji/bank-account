package com.sg.bankaccount.domain;

import com.sg.bankaccount.domain.bankaccount.AccountDomain;
import com.sg.bankaccount.domain.bankaccount.AmountDomain;
import com.sg.bankaccount.domain.bankaccount.BalanceDomain;
import com.sg.bankaccount.domain.exception.InsufficientBalanceException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountDomainTest {

    @Test
    public void doNothing() {
        
    }

    @Test
    public void shouldSaveMoneyWhenMakeADepositInAccount() {
        AmountDomain amountDomain = AmountDomain.from(new BigDecimal(100));
        BalanceDomain balanceDomain = BalanceDomain.from(amountDomain);
        AccountDomain accountDomain = AccountDomain.from(balanceDomain);
        AmountDomain amountToDeposit = AmountDomain.from(new BigDecimal(50));
        accountDomain = accountDomain.deposit(amountToDeposit);
        BalanceDomain expectedBalance = BalanceDomain.from(AmountDomain.from(new BigDecimal(150)));
        assertEquals(expectedBalance, accountDomain.getBalance());
    }

    @Test
    public void shouldMakeWithdrawalWhenWhenRetrieveMoneyAndThereIsEnoughMoney() {
        AmountDomain amountDomain = AmountDomain.from(new BigDecimal(100));
        BalanceDomain balanceDomain = BalanceDomain.from(amountDomain);
        AccountDomain accountDomain = AccountDomain.from(balanceDomain);
        AmountDomain amountToWithdrawal = AmountDomain.from(new BigDecimal(50));
        accountDomain = accountDomain.withdrawal(amountToWithdrawal);
        BalanceDomain expectedBalance = BalanceDomain.from(AmountDomain.from(new BigDecimal(50)));
        assertEquals(expectedBalance, accountDomain.getBalance());
    }

    @Test
    public void shouldNotMakeWithdrawalWhenRetrieveMoneyAndThereIsNotEnoughMoney() {
        AmountDomain amount = AmountDomain.from(new BigDecimal(100));
        BalanceDomain balanceDomain = BalanceDomain.from(amount);
        AccountDomain accountDomain = AccountDomain.from(balanceDomain);
        AmountDomain amountToWithdrawal = AmountDomain.from(new BigDecimal(150));
        InsufficientBalanceException  insufficientBalanceException =
                assertThrows(InsufficientBalanceException.class, () -> accountDomain.withdrawal(amountToWithdrawal));
        String expectedMessage = "Balance insufficient for your operation";
        assertEquals(expectedMessage, insufficientBalanceException.getMessage());
    }

}
