package com.sg.bankaccount.infrastructure.entities;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

public class EventStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long eventId;

    private String eventType;

    private String entityId;

    private String eventData;

    private LocalDateTime eventTime;
}
