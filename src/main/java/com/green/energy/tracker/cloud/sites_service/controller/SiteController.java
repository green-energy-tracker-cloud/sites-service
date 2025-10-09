package com.green.energy.tracker.cloud.sites_service.controller;

import com.green.energy.tracker.cloud.sites_service.model.Site;
import com.green.energy.tracker.cloud.sites_service.service.SiteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sites/v1")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "502", description = "Bad Gateway", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "503", description = "Service Unavailable", content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "504", description = "Gateway Timeout", content = {@Content(mediaType = "application/json")})
})
public class SiteController {

    private final SiteService siteService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Crea un nuovo sito",
            description = "Crea un nuovo sito con i dati forniti nel body della richiesta"
    )
    @ApiResponse(responseCode = "201", description = "Sito creato con successo",
            content = @Content(schema = @Schema(implementation = Site.class)))
    public ResponseEntity<Site> createSite(@Valid @RequestBody Site request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                siteService.save(request.getUserId(), request.getName(), request.getDescription(), request.getLocation())
        );
    }

    @PutMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Aggiorna un sito esistente",
            description = "Aggiorna i dati di un sito identificato dal nome"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sito aggiornato con successo", content = @Content(schema = @Schema(implementation = Site.class))),
            @ApiResponse(responseCode = "404", description = "Sito non trovato")
    })
    public ResponseEntity<Site> updateSite(@Parameter(description = "Nome del sito da aggiornare", required = true) @PathVariable @NotBlank String name,
                                           @Valid @RequestBody Site site) throws ExecutionException, InterruptedException {
        return siteService.update(name, site).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping(path = "/{name}")
    @Operation(
            summary = "Elimina un sito",
            description = "Elimina un sito identificato dal nome"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sito eliminato con successo"),
            @ApiResponse(responseCode = "404", description = "Sito non trovato")
    })
    public ResponseEntity<Void> deleteSite(
            @Parameter(description = "Nome del sito da eliminare", required = true)
            @PathVariable @NotBlank String name) throws ExecutionException, InterruptedException {
        return siteService.delete(name) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Recupera tutti i siti",
            description = "Restituisce l'elenco completo di tutti i siti"
    )
    @ApiResponse(responseCode = "200", description = "Lista dei siti recuperata con successo", content = @Content(schema = @Schema(implementation = Site.class)))
    public ResponseEntity<List<Site>> getAllSites() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(siteService.getAll());
    }

    @GetMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Recupera un sito per nome",
            description = "Restituisce i dettagli di un sito identificato dal nome"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sito trovato", content = @Content(schema = @Schema(implementation = Site.class))),
            @ApiResponse(responseCode = "404", description = "Sito non trovato")
    })
    public ResponseEntity<Site> getSiteByName(
            @Parameter(description = "Nome del sito da recuperare", required = true)
            @PathVariable @NotBlank String name) throws ExecutionException, InterruptedException {
        return siteService.getByName(name).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Recupera siti per userId",
            description = "Restituisce tutti i siti associati a uno specifico utente"
    )
    @ApiResponse(responseCode = "200", description = "Lista dei siti recuperata con successo", content = @Content(schema = @Schema(implementation = Site.class)))
    public ResponseEntity<List<Site>> getSitesByUserId(
            @Parameter(description = "ID dell'utente", required = true)
            @PathVariable @NotBlank String userId) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(siteService.getByUserId(userId));
    }
}
