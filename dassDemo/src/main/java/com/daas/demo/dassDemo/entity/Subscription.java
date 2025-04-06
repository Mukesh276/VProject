package com.daas.demo.dassDemo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
//@Table(name = "SUBSCRIPTION")  --- not working in Mysql Aurora atm, need to be looked into
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUBSCRIPTION_ID")
    private Long subscriptionId;

    @Column(name = "LOAD_TYPE")
    private String loadType;

    @Column(name = "JOB_DEFINITION")
    private String jobDefinition;

    @Column(name = "JOB_QUEUE")
    private String jobQueue;

    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("subscriptionRunId DESC")
    private List<SubscriptionRun> subscriptionRuns; // One-to-many relationship

}

