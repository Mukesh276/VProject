package com.daas.demo.dassDemo.controller;

import com.daas.demo.dassDemo.model.SubscriptionActiveStatus;
import com.daas.demo.dassDemo.service.DaasProcessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/daas/subscription")
@RequiredArgsConstructor
public class DaasSubscriptionController {

    private final DaasProcessorService daasProcessorService;

    @PostMapping("/processor")
    //If subId is not a number, it will be rejected from the openApi validator - yet to implement
    public ResponseEntity<String> processSubscription(@RequestParam Long subId) {
            daasProcessorService.processService(subId);
            return ResponseEntity.ok("Success");
    }

    @GetMapping("/active")
    public ResponseEntity<List<SubscriptionActiveStatus>> getActiveSubscriptions(
            @RequestHeader("client_id") String clientId,
            @RequestHeader("client_secret") String clientSecret,
            @RequestParam String companyCode,
            @RequestParam String client,
            @RequestParam String env) {

        List<SubscriptionActiveStatus> mockResponse = new ArrayList<>();
        // Fill with sample/mock data
        return ResponseEntity.ok(mockResponse);
    }

    @GetMapping("/{subscriptionId}/status")
    public ResponseEntity<Map<String, String>> getSubscriptionStatus(
            @RequestHeader("client_id") String clientId,
            @RequestHeader("client_secret") String clientSecret,
            @PathVariable String subscriptionId) {

        Map<String, String> response = new HashMap<>();
        response.put("SubscriptionId", subscriptionId);
        response.put("Status", "COMPLETED");
        return ResponseEntity.ok(response);
    }
}
