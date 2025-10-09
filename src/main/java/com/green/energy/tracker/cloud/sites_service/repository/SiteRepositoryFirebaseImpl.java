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
    public Site save(Site site) throws ExecutionException, InterruptedException {
        firestoreClient.collection(SITES_COLLECTION)
                .document(site.getSiteId())
                .set(site)
                .get();
        log.info("Site saved with ID: {}", site.getSiteId());
        return site;
    }

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

    @Override
    public Optional<Site> update(String name, Site updatedSite) throws ExecutionException, InterruptedException {
        var optSite = getByName(name);
        if (optSite.isEmpty()) {
            log.info("Site not found with name: {}", name);
            return Optional.empty();
        }
        return Optional.of(save(updatedSite));
    }

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
}
