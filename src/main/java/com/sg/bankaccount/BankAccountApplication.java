package com.sg.bankaccount;

import com.sg.bankaccount.domain.bankaccount.AccountDomain;
import com.sg.bankaccount.domain.bankaccount.AmountDomain;
import com.sg.bankaccount.domain.exception.BankAccountNotFoundException;
import com.sg.bankaccount.domain.exception.InsufficientBalanceException;
import com.sg.bankaccount.domain.service.AccountService;
import com.sg.bankaccount.infrastructure.entities.AccountEntity;
import com.sg.bankaccount.infrastructure.entities.OperationEntity;
import com.sg.bankaccount.infrastructure.exception.TechnicalException;
import com.sg.bankaccount.infrastructure.repositories.AccountRepository;
import com.sg.bankaccount.infrastructure.repositories.OperationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@SpringBootApplication
public class BankAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankAccountApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(AccountRepository accountRepository, AccountService accountService, OperationRepository operationRepository) {
        return arg -> {
            String accountNumber = "123456789";
            createNewAccount(accountNumber, accountRepository);
            deposit(accountNumber, new BigDecimal(100), accountService);
            withdrawal(accountNumber, new BigDecimal(50), accountService);
            withdrawal(accountNumber, new BigDecimal(150), accountService);
            deposit(accountNumber, new BigDecimal(200), accountService);
            withdrawal(accountNumber, new BigDecimal(210), accountService);

            printOperations(accountNumber, operationRepository);
            withdrawal(accountNumber+"2", new BigDecimal(201), accountService);
        };
    }

    private void createNewAccount(String accountNumber, AccountRepository accountRepository) {
        System.out.println("Create Account with balance of 0");
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setBalance(new BigDecimal(0));
        accountEntity.setAccountNumber(accountNumber);
        accountRepository.save(accountEntity);
    }

    private void deposit(String accountNumber,BigDecimal amount, AccountService accountService) {
        try {
            System.out.println("Deposit amount of " +amount.doubleValue()+ " Account "+accountNumber);
            AccountDomain accountDomain = accountService.deposit(accountNumber, AmountDomain.from(amount));
            System.out.println("The balance of account "+accountNumber+" is : " + accountDomain.getBalance().getAmountDomain().getValue());
        } catch (BankAccountNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (TechnicalException e) {
            System.out.println(e.getMessage());
        }
    }

    private void withdrawal(String accountNumber,BigDecimal amount, AccountService accountService) {
        try {
            System.out.println("Withdrawal amount of " +amount.doubleValue()+" from Account "+accountNumber);
            AccountDomain accountDomain = accountService.withdrawal(accountNumber, AmountDomain.from(amount));
            System.out.println("The balance of account is : " + accountDomain.getBalance().getAmountDomain().getValue());
        } catch (BankAccountNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (InsufficientBalanceException e) {
            System.out.println(e.getMessage());
        } catch (TechnicalException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printOperations(String accountNumber, OperationRepository operationRepository) {
         System.out.println("I want to print operations history of account "+accountNumber+ " : ");
        List<OperationEntity> operationEntities = operationRepository.findByAccountEntity_AccountNumberOrderByOperationDate(accountNumber);
        System.out.format("%20s%3s%25s%3s%20s", "Operation type", "  |", "Operation Date", "  |", "Operation amount\n");
        for (OperationEntity operationEntity: operationEntities) {
            System.out.format("%20s%3s%25s%3s%20s", operationEntity.getOperationType(), "  |", operationEntity.getOperationDate(), "  |", operationEntity.getAmount().doubleValue()+"\n");
        }
        System.out.println("The current balance is : "+operationEntities.get(0).getAccountEntity().getBalance());
    }

}
