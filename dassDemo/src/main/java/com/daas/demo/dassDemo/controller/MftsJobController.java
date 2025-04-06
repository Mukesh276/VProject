package com.daas.demo.dassDemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mfts-job")
public class MftsJobController {

    @PostMapping
    public ResponseEntity<String> triggerMftsJob(@RequestParam String subscriptionId) {
        // Call external MFTS service or mock response
        return ResponseEntity.ok("{ \"message\": \"MFTS job triggered successfully\" }");
    }
}
