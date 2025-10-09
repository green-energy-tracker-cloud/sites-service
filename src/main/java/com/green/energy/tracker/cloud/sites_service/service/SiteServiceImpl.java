package com.green.energy.tracker.cloud.sites_service.service;

import com.green.energy.tracker.cloud.sites_service.exception.FirestoreUnavailableException;
import com.green.energy.tracker.cloud.sites_service.model.Site;
import com.green.energy.tracker.cloud.sites_service.repository.SiteRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service("SiteServiceV1")
@RequiredArgsConstructor
@Slf4j
public class SiteServiceImpl implements SiteService{

    private final SiteRepository siteRepository;

    @CircuitBreaker(name = "firestoreCb", fallbackMethod = "saveFallback")
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
        throw new RuntimeException("Simulated failure");
        //return siteRepository.save(site);
    }

    @CircuitBreaker(name = "firestoreCb", fallbackMethod = "getByNameFallback")
    @Override
    public Optional<Site> getByName(String name) throws ExecutionException, InterruptedException {
        return siteRepository.getByName(name);
    }

    @CircuitBreaker(name = "firestoreCb", fallbackMethod = "updateFallback")
    @Override
    public Optional<Site> update(String name, Site updatedSite) throws ExecutionException, InterruptedException {
        return siteRepository.update(name, updatedSite);
    }

    @CircuitBreaker(name = "firestoreCb", fallbackMethod = "deleteFallback")
    @Override
    public Boolean delete(String name) throws ExecutionException, InterruptedException {
        return siteRepository.delete(name);
    }

    @CircuitBreaker(name = "firestoreCb", fallbackMethod = "getAllFallback")
    @Override
    public List<Site> getAll() throws ExecutionException, InterruptedException {
        return siteRepository.getAll();
    }

    @CircuitBreaker(name = "firestoreCb", fallbackMethod = "getByUserIdFallback")
    @Override
    public List<Site> getByUserId(String userId) throws ExecutionException, InterruptedException {
        return siteRepository.getByUserId(userId);
    }

    public Site saveFallback(String userId, String name, String description, String location, Throwable error) throws ExecutionException, InterruptedException {
        log.error("Error saving site: {}", error.getMessage());
        throw new FirestoreUnavailableException("Firestore is unavailable. Cannot save site.", error);
    }

    public Optional<Site> getByNameFallback(String name, Throwable error) throws ExecutionException, InterruptedException {
        log.error("Error retrieving site by name: {}", error.getMessage());
        throw new FirestoreUnavailableException("Firestore is unavailable. Cannot retrieve site by name.", error);
    }

    public Optional<Site> updateFallback(String name, Site updatedSite, Throwable error) throws ExecutionException, InterruptedException {
        log.error("Error updating site: {}", error.getMessage());
        throw new FirestoreUnavailableException("Firestore is unavailable. Cannot update site.", error);
    }

    public Boolean deleteFallback(String name, Throwable error) throws ExecutionException, InterruptedException {
        log.error("Error deleting site: {}", error.getMessage());
        throw new FirestoreUnavailableException("Firestore is unavailable. Cannot delete site.", error);
    }

    public List<Site> getAllFallback(Throwable error) throws ExecutionException, InterruptedException {
        log.error("Error retrieving all sites: {}", error.getMessage());
        throw new FirestoreUnavailableException("Firestore is unavailable. Cannot retrieve all sites.", error);
    }

    public List<Site> getByUserIdFallback(String userId, Throwable error) throws ExecutionException, InterruptedException {
        log.error("Error retrieving sites by userId: {}", error.getMessage());
        throw new FirestoreUnavailableException("Firestore is unavailable. Cannot retrieve sites by userId.", error);
    }
}
