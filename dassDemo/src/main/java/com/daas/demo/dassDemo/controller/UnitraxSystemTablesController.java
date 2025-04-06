package com.daas.demo.dassDemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/unitrax/system-tables")
public class UnitraxSystemTablesController {

    @PostMapping
    public ResponseEntity<String> getSystemTableCounts(@RequestBody Map<String, Object> request) {
        // Example recon logic or mock response
        return ResponseEntity.ok("{ \"totalCount\": 1234 }");
    }
}

