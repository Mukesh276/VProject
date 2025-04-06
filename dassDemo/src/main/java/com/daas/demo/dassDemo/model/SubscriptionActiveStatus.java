package com.daas.demo.dassDemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SubscriptionActiveStatus {
    private Long subscriptionId;
    private String subscriptionContact;
    private String startDateTime;
    private String endDateTime;
    private String status;

    // Not used yet
}
