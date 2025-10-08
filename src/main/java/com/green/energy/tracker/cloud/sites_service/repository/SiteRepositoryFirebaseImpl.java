package com.green.energy.tracker.cloud.sites_service.repository;

import com.google.cloud.firestore.Firestore;
import com.green.energy.tracker.cloud.sites_service.model.Site;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
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
        try {
            return firestoreClient.collection(SITES_COLLECTION)
                    .whereEqualTo("name", name)
                    .get()
                    .get()
                    .getDocuments()
                    .stream()
                    .map(queryDocumentSnapshot -> queryDocumentSnapshot.toObject(Site.class))
                    .findFirst();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error retrieving site by name: {}", e.getMessage());
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Site> update(String name, Site updatedSite) {
        var optSite = getByName(name);
        if (optSite.isEmpty()) {
            log.info("Site not found with name: {}", name);
            return Optional.empty();
        }
        return Optional.of(save(updatedSite));
    }

    @Override
    public Boolean delete(String name) {
        var optSite = getByName(name);
        if (optSite.isPresent()) {
            try {
                firestoreClient.collection(SITES_COLLECTION)
                        .document(optSite.get().getSiteId())
                        .delete()
                        .get();
                log.info("Site deleted with name: {}", name);
                return true;
            } catch (InterruptedException | ExecutionException e) {
                log.error("Error deleting site: {}", e.getMessage());
                Thread.currentThread().interrupt();
                return false;
            }
        } else {
            log.warn("Site not found with name: {}", name);
            return false;
        }
    }

    @Override
    public List<Site> getAll() {
        try {
            return firestoreClient.collection(SITES_COLLECTION)
                    .get()
                    .get()
                    .getDocuments()
                    .stream()
                    .map(queryDocumentSnapshot -> queryDocumentSnapshot.toObject(Site.class))
                    .toList();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error retrieving all sites: {}", e.getMessage());
            Thread.currentThread().interrupt();
            return List.of();
        }
    }

    @Override
    public List<Site> getByUserId(String userId) {
        try {
            return firestoreClient.collection(SITES_COLLECTION)
                    .whereEqualTo("userId", userId)
                    .get()
                    .get()
                    .getDocuments()
                    .stream()
                    .map(queryDocumentSnapshot -> queryDocumentSnapshot.toObject(Site.class))
                    .toList();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error retrieving sites by userId: {}", e.getMessage());
            Thread.currentThread().interrupt();
            return List.of();
        }
    }
}
