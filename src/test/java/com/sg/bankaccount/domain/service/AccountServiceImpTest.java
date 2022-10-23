package com.sg.bankaccount.domain.service;

import com.sg.bankaccount.domain.AccountDomain;
import com.sg.bankaccount.domain.AmountDomain;
import com.sg.bankaccount.domain.BalanceDomain;
import com.sg.bankaccount.domain.exception.BankAccountNotFoundException;
import com.sg.bankaccount.domain.exception.InsufficientBalanceException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@SpringBootTest
class AccountServiceImpTest {

    public static final String ACCOUNT_NUMBER = "123456789";

    @Mock
    private AccountPort accountPort;

    @InjectMocks
    private AccountService accountService = new AccountServiceImp(accountPort);

    @Test
    public void doNothing() {

    }

    @Test
    public void shouldSaveMoneyWhenMakeADepositInAccount() {
        when(accountPort.findBalanceByAccountNumber(ACCOUNT_NUMBER)).thenReturn(BalanceDomain.from(AmountDomain.from(new BigDecimal(100))));
        AccountDomain accountDomain = accountService.deposit(ACCOUNT_NUMBER, AmountDomain.from(new BigDecimal(50)));
        BalanceDomain expectedBalanceDomain = BalanceDomain.from(AmountDomain.from(new BigDecimal(150)));
        assertEquals(expectedBalanceDomain, accountDomain.getBalance());
    }

    @Test
    public void shouldThrowAccountNotFoundExceptionWhenAccountIsNotFoundForDeposit() {
        when(accountPort.findBalanceByAccountNumber(ACCOUNT_NUMBER)).thenThrow(BankAccountNotFoundException.class);
        BankAccountNotFoundException  bankAccountNotFoundException = assertThrows(BankAccountNotFoundException.class, () -> accountService.deposit(ACCOUNT_NUMBER, AmountDomain.from(new BigDecimal(100))));
        String expectedMessage = "The bank account is not found";
        assertEquals(expectedMessage, bankAccountNotFoundException.getMessage());
    }

    @Test
    public void shouldMakeWithdrawalWhenWhenRetrieveMoneyAndThereIsEnoughMoney() {
        when(accountPort.findBalanceByAccountNumber(ACCOUNT_NUMBER)).thenReturn(BalanceDomain.from(AmountDomain.from(new BigDecimal(150))));
        AccountDomain accountDomain = accountService.withdrawal(ACCOUNT_NUMBER, AmountDomain.from(new BigDecimal(90)));
        BalanceDomain expectedBalanceDomain = BalanceDomain.from(AmountDomain.from(new BigDecimal(60)));
        assertEquals(expectedBalanceDomain, accountDomain.getBalance());
    }

    @Test
    public void shouldThrowAccountNotFoundExceptionWhenAccountIsNotFoundForWithdrawal() {
        when(accountPort.findBalanceByAccountNumber(ACCOUNT_NUMBER)).thenThrow(BankAccountNotFoundException.class);
        BankAccountNotFoundException  bankAccountNotFoundException = assertThrows(BankAccountNotFoundException.class,
                () -> accountService.withdrawal(ACCOUNT_NUMBER, AmountDomain.from(new BigDecimal(100))));
        String expectedMessage = "The bank account is not found";
        assertEquals(expectedMessage, bankAccountNotFoundException.getMessage());
    }

    @Test
    public void shouldNotMakeWithdrawalWhenRetrieveMoneyAndThereIsNotEnoughMoney() {
        when(accountPort.findBalanceByAccountNumber(ACCOUNT_NUMBER)).thenReturn(BalanceDomain.from(AmountDomain.from(new BigDecimal(150))));
        InsufficientBalanceException insufficientBalanceException = assertThrows(InsufficientBalanceException.class,
                ()  -> accountService.withdrawal(ACCOUNT_NUMBER, AmountDomain.from(new BigDecimal(200))));
        String expectedMessage = "Balance insufficient for your operation";
        assertEquals(expectedMessage, insufficientBalanceException.getMessage());
    }
}
