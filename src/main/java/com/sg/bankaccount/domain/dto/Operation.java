package com.sg.bankaccount.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Operation {

    private String accountNumber;
    private BigDecimal amount;
    private BigDecimal balance;
    private LocalDateTime operationDate;
}
