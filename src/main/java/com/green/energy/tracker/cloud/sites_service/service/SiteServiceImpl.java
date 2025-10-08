package com.green.energy.tracker.cloud.sites_service.service;

import com.google.cloud.firestore.Firestore;
import com.green.energy.tracker.cloud.sites_service.model.Site;
import com.green.energy.tracker.cloud.sites_service.repository.SiteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("SiteServiceV1")
@RequiredArgsConstructor
public class SiteServiceImpl implements SiteService{

    private final SiteRepository siteRepository;

    @Override
    public Site save(String userId, String name, String description, String location) {
        var siteId = UUID.randomUUID().toString();
        var now = Instant.now();
        var site = Site.builder()
                .siteId(siteId)
                .userId(userId)
                .name(name)
                .description(description)
                .location(location)
                .creationDate(now)
                .lastUpdateDate(now)
                .build();
        return siteRepository.save(site);
    }

    @Override
    public Optional<Site> getByName(String name) {
        return siteRepository.getByName(name);
    }

    @Override
    public Optional<Site> update(String siteId, Site updatedSite) {
        return siteRepository.update(siteId, updatedSite);
    }

    @Override
    public Boolean delete(String name) {
        return siteRepository.delete(name);
    }

    @Override
    public List<Site> getAll() {
        return siteRepository.getAll();
    }

    @Override
    public List<Site> getByUserId(String userId) {
        return siteRepository.getByUserId(userId);
    }
}
