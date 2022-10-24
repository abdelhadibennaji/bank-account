package com.sg.bankaccount.domain.service;

import com.sg.bankaccount.domain.bankaccount.AccountDomain;
import com.sg.bankaccount.domain.bankaccount.AmountDomain;
import com.sg.bankaccount.domain.bankaccount.BalanceDomain;
import com.sg.bankaccount.domain.event.OperationEvent;
import com.sg.bankaccount.domain.event.CreditedAccountEvent;
import com.sg.bankaccount.domain.event.DebitedAccountEvent;
import com.sg.bankaccount.domain.exception.BankAccountNotFoundException;
import com.sg.bankaccount.domain.exception.InsufficientBalanceException;

import java.time.LocalDateTime;

public class AccountServiceImp implements AccountService {

    private AccountPort accountPort;

    public AccountServiceImp(AccountPort accountPort) {
        this.accountPort = accountPort;
    }

    @Override
    public AccountDomain deposit(String accountNumber, AmountDomain amountDomain) {
        try {
            AccountDomain accountDomain = getAccountDomainByAccountNumber(accountNumber);
            accountDomain = accountDomain.deposit(amountDomain);
            publishDebitedAccountEvent(accountDomain, accountNumber, amountDomain);
            return accountDomain;
        } catch (BankAccountNotFoundException e) {
            throw new BankAccountNotFoundException("The bank account is not found");
        }
    }

    @Override
    public AccountDomain withdrawal(String accountNumber, AmountDomain amountDomain) {
        try {
            AccountDomain accountDomain = getAccountDomainByAccountNumber(accountNumber);
            accountDomain = accountDomain.withdrawal(amountDomain);
            publishCreditedAccountEvent(accountDomain, accountNumber, amountDomain);
            return accountDomain;
        } catch (BankAccountNotFoundException e) {
            throw new BankAccountNotFoundException("The bank account is not found");
        } catch (InsufficientBalanceException e) {
            throw new InsufficientBalanceException("Balance insufficient for your operation");
        }
    }


    private AccountDomain getAccountDomainByAccountNumber(String accountNumber) {
       return AccountDomain.from(findBalanceByAccountNumber(accountNumber));
    }

    private BalanceDomain findBalanceByAccountNumber(String accountNumber) {
        return accountPort.findBalanceByAccountNumber(accountNumber);
    }

    private void publishCreditedAccountEvent(AccountDomain accountDomain, String accountNumber, AmountDomain amountDomain) {
        CreditedAccountEvent creditedAccountEvent = CreditedAccountEvent.from(
                OperationEvent.from(
                        accountNumber, amountDomain.getValue().negate(),
                        accountDomain.getBalance().getAmountDomain().getValue(), LocalDateTime.now()
                ));
        accountPort.publishEvent(creditedAccountEvent);
    }

    private void publishDebitedAccountEvent(AccountDomain accountDomain, String accountNumber, AmountDomain amountDomain) {
        DebitedAccountEvent debitedAccountEvent = DebitedAccountEvent.from(
                OperationEvent.from(
                        accountNumber, amountDomain.getValue(),accountDomain.getBalance().getAmountDomain().getValue(),
                        LocalDateTime.now()
                ));
        accountPort.publishEvent(debitedAccountEvent);
    }
}
