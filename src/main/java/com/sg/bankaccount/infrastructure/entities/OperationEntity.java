package com.sg.bankaccount.infrastructure.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class OperationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    private LocalDateTime operationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private AccountEntity accountEntity;

    public enum OperationType {
        DEBIT, CREDIT
    }
}
