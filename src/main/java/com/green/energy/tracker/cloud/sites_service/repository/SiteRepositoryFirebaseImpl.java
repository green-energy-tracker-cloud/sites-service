package com.green.energy.tracker.cloud.sites_service.repository;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
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
public class SiteRepositoryFirebaseImpl implements SiteRepository {

    private static final String SITES_COLLECTION = "sites";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_USER_ID = "userId";

    private final Firestore firestoreClient;

    @Override
    public Site save(Site site) throws ExecutionException, InterruptedException {
        log.debug("Saving site with ID: {}", site.getSiteId());
        var writeResult = firestoreClient.collection(SITES_COLLECTION)
                .document(site.getSiteId())
                .set(site)
                .get();
        log.info("Site saved successfully. ID: {}, UpdateTime: {}", site.getSiteId(), writeResult.getUpdateTime());
        return site;
    }

    @Override
    public Optional<Site> getByName(String name) throws ExecutionException, InterruptedException {
        log.debug("Fetching site by name: {}", name);
        var documents = getDocumentsByField(FIELD_NAME,name,true);
        if (documents.isEmpty()) {
            log.debug("Site not found with name: {}", name);
            return Optional.empty();
        }
        log.debug("Site found with name: {}", name);
        return Optional.of(documents.get(0).toObject(Site.class));
    }

    @Override
    public Optional<Site> update(String name, Site updatedSite) throws ExecutionException, InterruptedException {
        log.debug("Updating site with name: {}", name);
        var documents = getDocumentsByField(FIELD_NAME,name,true);
        if (documents.isEmpty()) {
            log.warn("Site not found for update with name: {}", name);
            return Optional.empty();
        }
        var documentId = documents.get(0).getId();
        updatedSite.setSiteId(documentId);
        var writeResult = firestoreClient.collection(SITES_COLLECTION)
                .document(documentId)
                .set(updatedSite)
                .get();
        log.info("Site updated successfully. Name: {}, UpdateTime: {}", name, writeResult.getUpdateTime());
        return Optional.of(updatedSite);
    }

    @Override
    public Boolean delete(String name) throws ExecutionException, InterruptedException {
        log.debug("Deleting site with name: {}", name);
        var documents = getDocumentsByField(FIELD_NAME,name,true);
        if (documents.isEmpty()) {
            log.warn("Site not found for deletion with name: {}", name);
            return false;
        }
        var documentId = documents.get(0).getId();
        var writeResult = firestoreClient.collection(SITES_COLLECTION)
                .document(documentId)
                .delete()
                .get();
        log.info("Site deleted successfully. Name: {}, DeleteTime: {}", name, writeResult.getUpdateTime());
        return true;
    }

    @Override
    public List<Site> getAll() throws ExecutionException, InterruptedException {
        log.debug("Fetching all sites");
        var sites = getDocumentsByField("","",false)
                .stream()
                .map(doc -> doc.toObject(Site.class))
                .toList();
        log.info("Fetched {} sites", sites.size());
        return sites;
    }

    @Override
    public List<Site> getByUserId(String userId) throws ExecutionException, InterruptedException {
        log.debug("Fetching sites for userId: {}", userId);
        var sites = getDocumentsByField(FIELD_USER_ID,userId,true)
                .stream()
                .map(doc -> doc.toObject(Site.class))
                .toList();
        log.info("Fetched {} sites for userId: {}", sites.size(), userId);
        return sites;
    }

    private List<QueryDocumentSnapshot> getDocumentsByField(String fieldName, String value, boolean findOnlyOne) throws ExecutionException, InterruptedException {
        if(findOnlyOne)
            return firestoreClient.collection(SITES_COLLECTION)
                .whereEqualTo(fieldName, value)
                .limit(1)
                .get()
                .get()
                .getDocuments();
        else
            return firestoreClient.collection(SITES_COLLECTION)
                .get()
                .get()
                .getDocuments();
    }
}
