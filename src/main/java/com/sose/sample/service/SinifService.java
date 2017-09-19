package com.sose.sample.service;

import com.sose.sample.domain.Sinif;
import com.sose.sample.repository.SinifRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Sinif.
 */
@Service
@Transactional
public class SinifService {

    private final Logger log = LoggerFactory.getLogger(SinifService.class);

    private final SinifRepository sinifRepository;

    public SinifService(SinifRepository sinifRepository) {
        this.sinifRepository = sinifRepository;
    }

    /**
     * Save a sinif.
     *
     * @param sinif the entity to save
     * @return the persisted entity
     */
    public Sinif save(Sinif sinif) {
        log.debug("Request to save Sinif : {}", sinif);
        return sinifRepository.save(sinif);
    }

    /**
     *  Get all the sinifs.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Sinif> findAll() {
        log.debug("Request to get all Sinifs");
        return sinifRepository.findAll();
    }

    /**
     *  Get one sinif by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Sinif findOne(Long id) {
        log.debug("Request to get Sinif : {}", id);
        return sinifRepository.findOne(id);
    }

    /**
     *  Delete the  sinif by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Sinif : {}", id);
        sinifRepository.delete(id);
    }
}
