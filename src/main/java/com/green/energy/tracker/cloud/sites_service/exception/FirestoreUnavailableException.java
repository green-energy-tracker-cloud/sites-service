package com.green.energy.tracker.cloud.sites_service.exception;

public class FirestoreUnavailableException extends RuntimeException {
    public FirestoreUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}