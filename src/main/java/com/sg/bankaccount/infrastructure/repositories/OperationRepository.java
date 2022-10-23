package com.sg.bankaccount.infrastructure.repositories;

import com.sg.bankaccount.infrastructure.entities.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<OperationEntity, Long> {
    List<OperationEntity> findByAccountEntity_AccountNumber(String accountNumber);
}
