package com.green.energy.tracker.cloud.sites_service.repository;

import com.google.cloud.firestore.Firestore;
import com.green.energy.tracker.cloud.sites_service.exception.FirestoreUnavailableException;
import com.green.energy.tracker.cloud.sites_service.model.Site;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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

    @CircuitBreaker(name = "firestoreCb", fallbackMethod = "saveFallback")
    @Override
    public Site save(Site site) throws ExecutionException, InterruptedException {
        throw new ExecutionException(new Throwable("Simulated Firestore failure"));
        //firestoreClient.collection(SITES_COLLECTION)
        //        .document(site.getSiteId())
        //        .set(site)
        //        .get();
        //log.info("Site saved with ID: {}", site.getSiteId());
        //return site;
    }

    @CircuitBreaker(name = "firestoreCb", fallbackMethod = "getByNameFallback")
    @Override
    public Optional<Site> getByName(String name) throws ExecutionException, InterruptedException {
        return firestoreClient.collection(SITES_COLLECTION)
                .whereEqualTo("name", name)
                .get()
                .get()
                .getDocuments()
                .stream()
                .map(queryDocumentSnapshot -> queryDocumentSnapshot.toObject(Site.class))
                .findFirst();
    }

    @CircuitBreaker(name = "firestoreCb", fallbackMethod = "updateFallback")
    @Override
    public Optional<Site> update(String name, Site updatedSite) throws ExecutionException, InterruptedException {
        var optSite = getByName(name);
        if (optSite.isEmpty()) {
            log.info("Site not found with name: {}", name);
            return Optional.empty();
        }
        return Optional.of(save(updatedSite));
    }

    @CircuitBreaker(name = "firestoreCb", fallbackMethod = "deleteFallback")
    @Override
    public Boolean delete(String name) throws ExecutionException, InterruptedException {
        var optSite = getByName(name);
        if (optSite.isEmpty()) {
            log.warn("Site not found with name: {}", name);
            return false;
        }
        firestoreClient.collection(SITES_COLLECTION)
                .document(optSite.get().getSiteId())
                .delete()
                .get();
        log.info("Site deleted with name: {}", name);
        return true;
    }

    @CircuitBreaker(name = "firestoreCb", fallbackMethod = "getAllFallback")
    @Override
    public List<Site> getAll() throws ExecutionException, InterruptedException {
        return firestoreClient.collection(SITES_COLLECTION)
                .get()
                .get()
                .getDocuments()
                .stream()
                .map(queryDocumentSnapshot -> queryDocumentSnapshot.toObject(Site.class))
                .toList();
    }

    @CircuitBreaker(name = "firestoreCb", fallbackMethod = "getByUserIdFallback")
    @Override
    public List<Site> getByUserId(String userId) throws ExecutionException, InterruptedException {
        return firestoreClient.collection(SITES_COLLECTION)
                .whereEqualTo("userId", userId)
                .get()
                .get()
                .getDocuments()
                .stream()
                .map(queryDocumentSnapshot -> queryDocumentSnapshot.toObject(Site.class))
                .toList();
    }

    @Override
    public Site saveFallback(Site site, Throwable error) {
        log.error("Error saving site: {}", error.getMessage());
        throw new FirestoreUnavailableException("Firestore is unavailable. Cannot save site.", error);
    }

    @Override
    public Optional<Site> getByNameFallback(String name, Throwable error) {
        log.error("Error retrieving site by name: {}", error.getMessage());
        throw new FirestoreUnavailableException("Firestore is unavailable. Cannot retrieve site by name.", error);
    }

    @Override
    public Optional<Site> updateFallback(String name, Site updatedSite, Throwable error) {
        log.error("Error updating site: {}", error.getMessage());
        throw new FirestoreUnavailableException("Firestore is unavailable. Cannot update site.", error);
    }

    @Override
    public Boolean deleteFallback(String name, Throwable error) {
        log.error("Error deleting site: {}", error.getMessage());
        throw new FirestoreUnavailableException("Firestore is unavailable. Cannot delete site.", error);
    }

    @Override
    public List<Site> getAllFallback(Throwable error) {
        log.error("Error retrieving all sites: {}", error.getMessage());
        throw new FirestoreUnavailableException("Firestore is unavailable. Cannot retrieve all sites.", error);
    }

    @Override
    public List<Site> getByUserIdFallback(String userId, Throwable error) {
        log.error("Error retrieving sites by userId: {}", error.getMessage());
        throw new FirestoreUnavailableException("Firestore is unavailable. Cannot retrieve sites by userId.", error);
    }
}
