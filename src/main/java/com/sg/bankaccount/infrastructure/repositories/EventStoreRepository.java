package com.sg.bankaccount.infrastructure.repositories;

import com.sg.bankaccount.infrastructure.entities.EventStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventStoreRepository extends JpaRepository<EventStore, Long> {
    Iterable<EventStore> findByEntityId(String entityId);
}
