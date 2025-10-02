package com.green.energy.tracker.cloud.sites_service.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Site {
    private String siteId;
    private String userId;
    private String name;
    private String description;
    private String location;
    private Instant creationDate;
    private Instant lastUpdateDate;
}
