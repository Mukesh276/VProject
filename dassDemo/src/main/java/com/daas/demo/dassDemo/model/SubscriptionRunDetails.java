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
public class SubscriptionRunDetails {
    private LoadType loadType;
    private String jobDefinition;
    private String jobQueue;
    private LocalDateTime subscriptionEndDateTime;
    private SubscriptionStatus runStatus;
    private Long subscriptionRunId;
}

