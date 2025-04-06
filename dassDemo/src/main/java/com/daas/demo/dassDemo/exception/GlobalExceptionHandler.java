package com.daas.demo.dassDemo.exception;


import com.daas.demo.dassDemo.errormodel.SubscriptionMessageError;
import com.daas.demo.dassDemo.errormodel.SubscriptionStatusError;
import com.daas.demo.dassDemo.external.WebClientCalls;
import com.daas.demo.dassDemo.repository.SubscriptionRepository;
import com.daas.demo.dassDemo.repository.SubscriptionRunRepository;
import com.daas.demo.dassDemo.utility.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionRunRepository subscriptionRunRepository;
    private WebClientCalls webClientCalls;

    @ExceptionHandler(SubscriptionStatusNotValid.class)
    public ResponseEntity<SubscriptionMessageError> handleSubscriptionStatusNotValid(SubscriptionStatusNotValid ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrorMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SubscriptionStatusError> handleBadRequest(Exception ex) {

        Long subscriptionId = RequestContextHolder.getSubId();

        SubscriptionStatusError errorMessage = new SubscriptionStatusError("Failed",
                String.valueOf(subscriptionId), "Issue in submitting the subscription for DAAS file generation");

        //call DB operations

        //call 3rd party api

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

}

