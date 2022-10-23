package com.sg.bankaccount.config;

import com.sg.bankaccount.domain.service.AccountPort;
import com.sg.bankaccount.domain.service.AccountService;
import com.sg.bankaccount.domain.service.AccountServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BankAccountConfig {

    @Bean
    public AccountService accountService(AccountPort accountPort) {
        return new AccountServiceImp(accountPort);
    }

}
