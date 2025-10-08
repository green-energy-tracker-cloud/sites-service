package com.green.energy.tracker.cloud.sites_service.repository;

import com.green.energy.tracker.cloud.sites_service.model.Site;

import java.util.List;
import java.util.Optional;

public interface SiteRepository {
    /**
     * Crea un nuovo sito.
     *
     * @param site sito da creare
     */
    Site save(Site site);

    /**
     * Recupera un sito dato il suo nome.
     *
     * @param name nome del sito da recuperare
     * @return Site se trovato, altrimenti Optional vuoto
     */
    Optional<Site> getByName(String name);

    /**
     * Aggiorna un sito esistente.
     *
     * @param siteId ID del sito da aggiornare
     * @param updatedSite dati aggiornati del sito
     * @return Site aggiornato
     */
    Optional<Site> update(String siteId, Site updatedSite);

    /**
     * Elimina un sito dato il suo nome.
     *
     * @param name nome del sito da eliminare
     */
    Boolean delete(String name);

    /**
     * Restituisce tutti i siti presenti nel database.
     *
     * @return lista di Site
     */
    List<Site> getAll();

    /**
     * Trova tutti i siti associati a uno specifico utente.
     *
     * @param userId ID utente
     * @return lista di Site per l’utente
     */
    List<Site> getByUserId(String userId);
}
