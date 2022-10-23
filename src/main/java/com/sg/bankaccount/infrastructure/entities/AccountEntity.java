package com.sg.bankaccount.infrastructure.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class AccountEntity {

    @Id
    private String accountNumber;

    private BigDecimal balance;

    @OneToMany(mappedBy = "accountEntity")
    private List<OperationEntity>   operationEntities;

}
