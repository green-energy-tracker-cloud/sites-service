package com.green.energy.tracker.cloud.sites_service.controller;

import com.green.energy.tracker.cloud.sites_service.exception.FirestoreUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

public class SiteControllerAdvice {
    @ExceptionHandler(FirestoreUnavailableException.class)
    public ResponseEntity<Map<String, String>> handleFirestoreUnavailable(FirestoreUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of("error", "FIRESTORE_UNAVAILABLE", "message", ex.getMessage()));
    }
}
