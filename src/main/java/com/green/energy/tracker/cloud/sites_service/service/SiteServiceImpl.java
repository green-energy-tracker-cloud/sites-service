package com.green.energy.tracker.cloud.sites_service.service;

import com.green.energy.tracker.cloud.sites_service.model.Site;
import com.green.energy.tracker.cloud.sites_service.repository.SiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service("SiteServiceV1")
@RequiredArgsConstructor
public class SiteServiceImpl implements SiteService{

    private final SiteRepository siteRepository;

    @Override
    public Site save(String userId, String name, String description, String location) throws ExecutionException, InterruptedException {
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
    public Optional<Site> getByName(String name) throws ExecutionException, InterruptedException {
        return siteRepository.getByName(name);
    }

    @Override
    public Optional<Site> update(String name, Site updatedSite) throws ExecutionException, InterruptedException {
        return siteRepository.update(name, updatedSite);
    }

    @Override
    public Boolean delete(String name) throws ExecutionException, InterruptedException {
        return siteRepository.delete(name);
    }

    @Override
    public List<Site> getAll() throws ExecutionException, InterruptedException {
        return siteRepository.getAll();
    }

    @Override
    public List<Site> getByUserId(String userId) throws ExecutionException, InterruptedException {
        return siteRepository.getByUserId(userId);
    }
}
