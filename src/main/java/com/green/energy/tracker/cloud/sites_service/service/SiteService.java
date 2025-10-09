package com.green.energy.tracker.cloud.sites_service.service;

import com.green.energy.tracker.cloud.sites_service.model.Site;
import java.util.List;
import java.util.Optional;
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
    Site save(String userId, String name, String description, String location) throws ExecutionException, InterruptedException;

    /**
     * Recupera un sito dato il suo nome.
     *
     * @param name nome del sito da recuperare
     * @return Site se trovato, altrimenti Optional vuoto
     */
    Optional<Site> getByName(String name) throws ExecutionException, InterruptedException;

    /**
     * Aggiorna un sito esistente.
     *
     * @param name name del sito da aggiornare
     * @param updatedSite dati aggiornati del sito
     * @return Site aggiornato
     */
    Optional<Site> update(String name, Site updatedSite) throws ExecutionException, InterruptedException;

    /**
     * Elimina un sito dato il suo nome.
     *
     * @param name nome del sito da eliminare
     */
    boolean delete(String name) throws ExecutionException, InterruptedException;

    /**
     * Restituisce tutti i siti presenti nel database.
     *
     * @return lista di Site
     */
    List<Site> getAll() throws ExecutionException, InterruptedException;

    /**
     * Trova tutti i siti associati a uno specifico utente.
     *
     * @param userId ID utente
     * @return lista di Site per l’utente
     */
    List<Site> getByUserId(String userId) throws ExecutionException, InterruptedException;
}
