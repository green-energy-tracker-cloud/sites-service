package com.green.energy.tracker.cloud.sites_service.repository;

import com.google.cloud.firestore.Firestore;
import com.green.energy.tracker.cloud.sites_service.model.Site;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Slf4j
public class SiteRepositoryFirebaseImpl implements SiteRepository{
    private final static String SITES_COLLECTION = "sites";
    private final Firestore firestoreClient;

    @Override
    public Site save(Site site) {
        try {
            firestoreClient.collection(SITES_COLLECTION)
                    .document(site.getSiteId())
                    .set(site)
                    .get();
            log.info("Site saved with ID: {}", site.getSiteId());
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error saving site: {}", e.getMessage());
            Thread.currentThread().interrupt();
            return null;
        }
        return site;
    }

    @Override
    public Optional<Site> getByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Site> update(String siteId, Site updatedSite) {
        return Optional.empty();
    }

    @Override
    public Boolean delete(String name) {
        return null;
    }

    @Override
    public List<Site> getAll() {
        return List.of();
    }

    @Override
    public List<Site> getByUserId(String userId) {
        return List.of();
    }
}
