package com.sg.bankaccount.domain.event;

import com.sg.bankaccount.domain.dto.Operation;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DebitedAccountEvent implements AccountEvent {
    Operation operation;
}
