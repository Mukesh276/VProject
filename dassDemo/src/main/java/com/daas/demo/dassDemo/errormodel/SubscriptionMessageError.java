package com.daas.demo.dassDemo.errormodel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubscriptionMessageError {

    private String subscriptionId;
    private String subscriptionRunId;
    private String message;

}

