package com.daas.demo.dassDemo.service;

import com.daas.demo.dassDemo.entity.Subscription;
import com.daas.demo.dassDemo.entity.SubscriptionRun;
import com.daas.demo.dassDemo.errormodel.SubscriptionMessageError;
import com.daas.demo.dassDemo.exception.SubscriptionStatusNotValid;
import com.daas.demo.dassDemo.model.SubscriptionJobDetails;
import com.daas.demo.dassDemo.model.SubscriptionRunDetails;
import com.daas.demo.dassDemo.repository.SubscriptionRepository;
import com.daas.demo.dassDemo.repository.SubscriptionRunRepository;
import com.daas.demo.dassDemo.service.iService.IPostProcessService;
import com.daas.demo.dassDemo.utility.LoadType;
import com.daas.demo.dassDemo.utility.SubscriptionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DaasProcessorService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionRunRepository subscriptionRunRepository;
    private final IPostProcessService iPostProcessService;

    public void processService(Long subId) {
        List<SubscriptionRunDetails> subscriptionRunDetailsList = retrieveSubscriptionData(subId);
        SubscriptionJobDetails subscriptionJobDetails = transformPayload(subscriptionRunDetailsList);
        log.info("LoadType: %s, JOB_DEFINITION: %s, JOB_QUEUE: %s, SubscriptionStatus: %s"
                .formatted(subscriptionJobDetails.getLoadType(), subscriptionJobDetails.getJobDef(),
                        subscriptionJobDetails.getJobQueue(), subscriptionJobDetails.getSubscriptionStatus()));

        if(subscriptionJobDetails.getSubscriptionStatus() == SubscriptionStatus.COMPLETED) {

            LocalDateTime subscriptionStartDate = null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime parsedDate = LocalDateTime.parse("2018-01-01 00:00:00", formatter);

            if(subscriptionJobDetails.getLoadType() == LoadType.DELTA) {
                subscriptionStartDate = subscriptionJobDetails.getExistingSubEndDate() != null? subscriptionJobDetails.getExistingSubEndDate(): parsedDate;
            } else if(subscriptionJobDetails.getLoadType() == LoadType.FULL) {
                subscriptionStartDate= parsedDate;
            } else {
                log.info("Invalid Load Type Identified");
            }

            // name="daas-rewriteSub_Flow::Preprocessing"/>
            //User autowired "iPostProcessService" to call the DB operations

        } else if(subscriptionJobDetails.getSubscriptionStatus() == SubscriptionStatus.ERROR) {
            log.info("Requested Subscription was on ERROR last time");

            LocalDateTime subscriptionStartDate = null;
            LocalDateTime subscriptionEndDate = null;

            Subscription subscriptionById = subscriptionRepository.findById(subId)
                    .orElseThrow(() -> new RuntimeException("ID " + subId + " not found in Subscription Database;"));
            List<SubscriptionRun> errorsList = subscriptionById.getSubscriptionRuns().stream()
                    .filter(t -> t.getRunStatus().equals("ERROR")).toList();

            subscriptionStartDate = errorsList.get(0).getSubscriptionStartDatetime();
            subscriptionEndDate = errorsList.get(0).getSubscriptionEndDatetime();

            //name="daas-rewriteSub_Flow::Preprocessing"/>
            //User autowired "iPostProcessService" to call the DB operations

        } else {
            SubscriptionMessageError errorMessage = new SubscriptionMessageError(String.valueOf(subId),
                    String.valueOf(subscriptionJobDetails.getRecentSubRunId()), "DAAS file generation for above subscription is already running");
            throw new SubscriptionStatusNotValid(errorMessage);
        }
    }

    @Transactional
    private List<SubscriptionRunDetails> retrieveSubscriptionData(Long subId) {
        Subscription subscriptionById = subscriptionRepository.findById(subId)
                .orElseThrow(() -> new RuntimeException("ID " + subId + " not found in Subscription Database;"));

        List<SubscriptionRun> subscriptionRuns = subscriptionById.getSubscriptionRuns();
        List<SubscriptionRunDetails> subscriptionRunDetailsList = new ArrayList<>();

        for (SubscriptionRun subscriptionRun : subscriptionRuns) {
            SubscriptionRunDetails subscriptionRunDetails = new SubscriptionRunDetails();
            subscriptionRunDetails.setLoadType(LoadType.valueOf(subscriptionById.getLoadType()));
            subscriptionRunDetails.setJobDefinition(subscriptionById.getJobDefinition());
            subscriptionRunDetails.setJobQueue(subscriptionById.getJobQueue());
            subscriptionRunDetails.setSubscriptionEndDateTime(subscriptionRun.getSubscriptionEndDatetime());
            subscriptionRunDetails.setRunStatus(SubscriptionStatus.valueOf(subscriptionRun.getRunStatus()));
            subscriptionRunDetails.setSubscriptionRunId(subscriptionRun.getSubscriptionRunId());
            subscriptionRunDetailsList.add(subscriptionRunDetails);
        }

        return subscriptionRunDetailsList;
    }


    private SubscriptionJobDetails transformPayload(List<SubscriptionRunDetails> subscriptionRunDetailsList) {

       // if(!subscriptionRunDetailsList.isEmpty()) -- not required as added @NON_NULL on top of the Model - SubscriptionJobDetails
            SubscriptionJobDetails subscriptionJobDetails = new SubscriptionJobDetails();
            subscriptionJobDetails.setLoadType(subscriptionRunDetailsList.get(0).getLoadType());
            subscriptionJobDetails.setExistingSubEndDate(subscriptionRunDetailsList.get(0).getSubscriptionEndDateTime());
            subscriptionJobDetails.setSubscriptionStatus(subscriptionRunDetailsList.get(0).getRunStatus());
            subscriptionJobDetails.setJobDef(subscriptionRunDetailsList.get(0).getJobDefinition());
            subscriptionJobDetails.setJobQueue(subscriptionRunDetailsList.get(0).getJobQueue());
            subscriptionJobDetails.setRecentSubRunId(subscriptionRunDetailsList.get(0).getSubscriptionRunId());
            return subscriptionJobDetails;
    }
}
