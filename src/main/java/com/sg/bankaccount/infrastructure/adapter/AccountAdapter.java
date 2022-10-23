package com.sg.bankaccount.infrastructure.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sg.bankaccount.domain.AmountDomain;
import com.sg.bankaccount.domain.BalanceDomain;
import com.sg.bankaccount.domain.enums.AccountEventType;
import com.sg.bankaccount.domain.event.CreditedAccountEvent;
import com.sg.bankaccount.domain.event.DebitedAccountEvent;
import com.sg.bankaccount.domain.exception.BankAccountNotFoundException;
import com.sg.bankaccount.domain.service.AccountPort;
import com.sg.bankaccount.infrastructure.entities.AccountEntity;
import com.sg.bankaccount.infrastructure.entities.EventStore;
import com.sg.bankaccount.infrastructure.entities.OperationEntity;
import com.sg.bankaccount.infrastructure.exception.TechnicalException;
import com.sg.bankaccount.infrastructure.repositories.AccountRepository;
import com.sg.bankaccount.infrastructure.repositories.EventStoreRepository;
import com.sg.bankaccount.infrastructure.repositories.OperationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountAdapter implements AccountPort {

    private EventStoreRepository eventStoreRepository;

    private AccountRepository accountRepository;

    private OperationRepository operationRepository;

    public AccountAdapter(EventStoreRepository eventStoreRepository, AccountRepository accountRepository, OperationRepository operationRepository) {
        this.eventStoreRepository = eventStoreRepository;
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    public BalanceDomain findBalanceByAccountNumber(String accountNumber) {
        AccountEntity accountEntity = accountRepository.findByAccountNumber(accountNumber);
        if(accountEntity==null) throw new BankAccountNotFoundException("The bank account is not found");
        return BalanceDomain.from(AmountDomain.from(accountEntity.getBalance()));
    }

    @Override
    public void saveEvent(CreditedAccountEvent event) {
        try {
            EventStore eventStore = new EventStore();
            ObjectMapper mapper = JsonMapper.builder()
                    .addModule(new JavaTimeModule())
                    .build();
            eventStore.setEventData(mapper.writeValueAsString(event.getOperation()));
            eventStore.setEventType(AccountEventType.ACCOUNT_CREDITED.name());
            eventStore.setEntityId(event.getOperation().getAccountNumber());
            eventStore.setEventTime(LocalDateTime.now());
            eventStoreRepository.save(eventStore);
            // after we saved the event in EventStore we can also publish this event with kafka or another broker to be consume by other micro services
            // For our case I'll save date in the same data base to be used for reading
            AccountEntity accountEntity = accountRepository.findByAccountNumber(event.getOperation().getAccountNumber());
            OperationEntity operationEntity = new OperationEntity();
            operationEntity.setAmount(event.getOperation().getAmount());
            operationEntity.setOperationDate(event.getOperation().getOperationDate());
            operationEntity.setOperationType(OperationEntity.OperationType.CREDIT);
            operationEntity.setAccountEntity(accountEntity);
            operationRepository.save(operationEntity);
            accountEntity.setBalance(event.getOperation().getBalance());
            accountRepository.save(accountEntity);
        } catch (JsonProcessingException e) {
            throw new TechnicalException("Error during converting object operation to json");
        }
    }

    @Override
    public void saveEvent(DebitedAccountEvent event) {
        try {
            EventStore eventStore = new EventStore();
            ObjectMapper mapper = JsonMapper.builder()
                    .addModule(new JavaTimeModule())
                    .build();
            eventStore.setEventData(mapper.writeValueAsString(event.getOperation()));
            eventStore.setEventType(AccountEventType.ACCOUNT_DEBITED.name());
            eventStore.setEntityId(event.getOperation().getAccountNumber());
            eventStore.setEventTime(LocalDateTime.now());
            
            eventStoreRepository.save(eventStore);

            // after we saved the event in EventStore we can also publish this event with kafka or another broker to be consume by other micro services
            // For our case I'll save date in the same data base to be used for reading
            AccountEntity accountEntity = accountRepository.findByAccountNumber(event.getOperation().getAccountNumber());
            OperationEntity operationEntity = new OperationEntity();
            operationEntity.setAmount(event.getOperation().getAmount());
            operationEntity.setOperationDate(event.getOperation().getOperationDate());
            operationEntity.setOperationType(OperationEntity.OperationType.DEBIT);
            operationEntity.setAccountEntity(accountEntity);
            operationRepository.save(operationEntity);
            accountEntity.setBalance(event.getOperation().getBalance());
            accountRepository.save(accountEntity);
        } catch (JsonProcessingException e) {
            throw new TechnicalException("Error during converting object operation to json");
        }

    }
}
