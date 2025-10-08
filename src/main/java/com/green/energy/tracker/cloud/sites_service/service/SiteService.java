package com.green.energy.tracker.cloud.sites_service.service;

import com.green.energy.tracker.cloud.sites_service.model.Site;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface SiteService {

    /**
     * Crea un nuovo sito.
     *
     * @param userId ID dell'utente proprietario del sito
     * @param name nome del sito
     * @param description descrizione del sito
     * @param location posizione del sito
     * @return Site creato
     * @throws Exception se si verifica un errore durante la creazione
     */
    Site save(String userId, String name, String description, String location) throws Exception;

    /**
     * Recupera un sito dato il suo nome.
     *
     * @param name nome del sito da recuperare
     * @return Site se trovato, altrimenti Optional vuoto
     */
    Optional<Site> getByName(String name) throws Exception;

    /**
     * Aggiorna un sito esistente.
     *
     * @param name name del sito da aggiornare
     * @param updatedSite dati aggiornati del sito
     * @return Site aggiornato
     */
    Optional<Site> update(String name, Site updatedSite) throws Exception;

    /**
     * Elimina un sito dato il suo nome.
     *
     * @param name nome del sito da eliminare
     */
    Boolean delete(String name) throws Exception;

    /**
     * Restituisce tutti i siti presenti nel database.
     *
     * @return lista di Site
     */
    List<Site> getAll() throws Exception;

    /**
     * Trova tutti i siti associati a uno specifico utente.
     *
     * @param userId ID utente
     * @return lista di Site per l’utente
     */
    List<Site> getByUserId(String userId)  throws Exception;
}
