package com.daas.demo.dassDemo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
//@Table(name = "SUBSCRIPTION_RUN")  --- not working in Mysql Aurora atm, need to be looked into
public class SubscriptionRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUBSCRIPTION_RUN_ID")
    private Long subscriptionRunId;

    @Column(name = "SUBSCRIPTION_START_DATETIME")
    private LocalDateTime subscriptionStartDatetime;

    @Column(name = "SUBSCRIPTION_END_DATETIME")
    private LocalDateTime subscriptionEndDatetime;

    @Column(name = "RUN_STATUS")
    private String runStatus;

    @ManyToOne
    @JoinColumn(name = "SUBSCRIPTION_ID")
    private Subscription subscription; // Many-to-one relationship
}

