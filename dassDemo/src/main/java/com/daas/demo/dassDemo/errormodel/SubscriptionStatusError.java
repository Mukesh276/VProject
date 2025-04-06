package com.daas.demo.dassDemo.errormodel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubscriptionStatusError {

    private String status;
    private String subscriptionId;
    private String description;
}

