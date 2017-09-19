package com.sose.sample.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sose.sample.domain.Sinif;
import com.sose.sample.service.SinifService;
import com.sose.sample.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Sinif.
 */
@RestController
@RequestMapping("/api")
public class SinifResource {

    private final Logger log = LoggerFactory.getLogger(SinifResource.class);

    private static final String ENTITY_NAME = "sinif";

    private final SinifService sinifService;

    public SinifResource(SinifService sinifService) {
        this.sinifService = sinifService;
    }

    /**
     * POST  /sinifs : Create a new sinif.
     *
     * @param sinif the sinif to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sinif, or with status 400 (Bad Request) if the sinif has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sinifs")
    @Timed
    public ResponseEntity<Sinif> createSinif(@RequestBody Sinif sinif) throws URISyntaxException {
        log.debug("REST request to save Sinif : {}", sinif);
        if (sinif.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sinif cannot already have an ID")).body(null);
        }
        Sinif result = sinifService.save(sinif);
        return ResponseEntity.created(new URI("/api/sinifs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sinifs : Updates an existing sinif.
     *
     * @param sinif the sinif to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sinif,
     * or with status 400 (Bad Request) if the sinif is not valid,
     * or with status 500 (Internal Server Error) if the sinif couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sinifs")
    @Timed
    public ResponseEntity<Sinif> updateSinif(@RequestBody Sinif sinif) throws URISyntaxException {
        log.debug("REST request to update Sinif : {}", sinif);
        if (sinif.getId() == null) {
            return createSinif(sinif);
        }
        Sinif result = sinifService.save(sinif);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sinif.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sinifs : get all the sinifs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sinifs in body
     */
    @GetMapping("/sinifs")
    @Timed
    public List<Sinif> getAllSinifs() {
        log.debug("REST request to get all Sinifs");
        return sinifService.findAll();
        }

    /**
     * GET  /sinifs/:id : get the "id" sinif.
     *
     * @param id the id of the sinif to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sinif, or with status 404 (Not Found)
     */
    @GetMapping("/sinifs/{id}")
    @Timed
    public ResponseEntity<Sinif> getSinif(@PathVariable Long id) {
        log.debug("REST request to get Sinif : {}", id);
        Sinif sinif = sinifService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sinif));
    }

    /**
     * DELETE  /sinifs/:id : delete the "id" sinif.
     *
     * @param id the id of the sinif to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sinifs/{id}")
    @Timed
    public ResponseEntity<Void> deleteSinif(@PathVariable Long id) {
        log.debug("REST request to delete Sinif : {}", id);
        sinifService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
