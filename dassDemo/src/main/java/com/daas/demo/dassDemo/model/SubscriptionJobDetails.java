package com.daas.demo.dassDemo.model;


import com.daas.demo.dassDemo.utility.LoadType;
import com.daas.demo.dassDemo.utility.SubscriptionStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionJobDetails {
    private LoadType loadType;
    private LocalDateTime existingSubEndDate;
    private SubscriptionStatus subscriptionStatus;
    private String jobDef;
    private String jobQueue;
    private Long recentSubRunId;
}
