package com.daas.demo.dassDemo.exception;

import com.daas.demo.dassDemo.errormodel.SubscriptionMessageError;

public class SubscriptionStatusNotValid extends RuntimeException {

    private SubscriptionMessageError errorMessage;

    public SubscriptionStatusNotValid(String message) {
        super(message);
    }

    public SubscriptionStatusNotValid(SubscriptionMessageError message) {
        this.errorMessage = message;
    }

    public SubscriptionMessageError getErrorMessage() {
        return errorMessage;
    }
}
