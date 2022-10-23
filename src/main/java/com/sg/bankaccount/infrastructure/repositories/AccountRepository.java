package com.sg.bankaccount.infrastructure.repositories;

import com.sg.bankaccount.infrastructure.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, String> {
    AccountEntity findByAccountNumber(String accountNumber);
}
