package com.green.energy.tracker.cloud.sites_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SiteControllerReceiveEvents {

    @PostMapping("/sites-events")
    public ResponseEntity<String> handleEvent(@RequestBody String cloudEventJson) {
        System.out.println("Evento Firestore ricevuto: " + cloudEventJson);
        return ResponseEntity.ok("Event received");
    }
}
