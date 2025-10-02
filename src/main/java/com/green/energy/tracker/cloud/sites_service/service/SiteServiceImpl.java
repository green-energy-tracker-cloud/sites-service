package com.green.energy.tracker.cloud.sites_service.service;

import com.green.energy.tracker.cloud.sites_service.model.Site;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("SiteServiceV1")
@RequiredArgsConstructor
public class SiteServiceImpl implements SiteService{



    @Override
    public Site createSite(String userId, String name, String description, String location) throws Exception {
        String siteId = UUID.randomUUID().toString();
        Instant now = Instant.now();
        return Site.builder()
                .siteId(siteId)
                .userId(userId)
                .name(name)
                .description(description)
                .location(location)
                .creationDate(now)
                .lastUpdateDate(now)
                .build();
    }

    @Override
    public Optional<Site> getSiteByName(String name) throws Exception {
        return Optional.empty();
    }

    @Override
    public Optional<Site> updateSite(String siteId, Site updatedSite) throws Exception {
        return Optional.empty();
    }

    @Override
    public Boolean deleteSite(String name) throws Exception {
        return true;
    }

    @Override
    public List<Site> getAllSites() throws Exception {
        return List.of();
    }

    @Override
    public List<Site> getSitesByUserId(String userId) throws Exception {
        return List.of();
    }
}
