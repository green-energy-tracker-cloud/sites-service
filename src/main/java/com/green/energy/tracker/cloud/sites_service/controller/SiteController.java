package com.green.energy.tracker.cloud.sites_service.controller;

import com.green.energy.tracker.cloud.sites_service.model.Site;
import com.green.energy.tracker.cloud.sites_service.service.SiteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sites/v1")
public class SiteController {

    private final SiteService siteService;

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Api per creazione Sito", description = " ",
            responses = {@ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
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

    public ResponseEntity<Site> createSite(
            @RequestParam(name = "userId") String userId,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "location") String location) throws Exception {
        return ResponseEntity.ok().body(siteService.createSite(userId, name, description, location));
    }

    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Api per aggiornamento dati Sito", description = " ",
            responses = {@ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
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

    public ResponseEntity<Site> updateSite(@RequestParam(name = "siteId") String siteId, @RequestBody Site site) throws Exception {
        return siteService.updateSite(siteId,site).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping(path = "/deleteByName", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Api per eliminazione dati Sito tramite nome", description = " ",
            responses = {@ApiResponse(responseCode = "204", description = "No Content", useReturnTypeSchema = true)})
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

    public ResponseEntity<Void> deleteByName(@RequestParam(name = "name") String name) throws Exception {
        return siteService.deleteSite(name) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Api per recupero informazioni di tutti i Siti", description = " ",
            responses = {@ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
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
    public ResponseEntity<List<Site>> getAllSites() throws Exception {
        return ResponseEntity.ok(siteService.getAllSites());
    }

    @GetMapping(path = "/findByName", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Api per recupero dati Sito tramite nome", description = " ",
            responses = {@ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
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

    public ResponseEntity<Site> getByName(@RequestParam(name = "name") String name) throws Exception {
        return siteService.getSiteByName(name).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/findByUserId", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Api per recupero dati Sito tramite userId", description = " ",
            responses = {@ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
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

    public ResponseEntity<List<Site>> getByUserId(@RequestParam(name = "userId") String userId) throws Exception {
        return ResponseEntity.ok(siteService.getSitesByUserId(userId));
    }
}
