package com.sose.sample.web.rest;

import com.sose.sample.SoseApp;

import com.sose.sample.domain.Sinif;
import com.sose.sample.repository.SinifRepository;
import com.sose.sample.service.SinifService;
import com.sose.sample.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SinifResource REST controller.
 *
 * @see SinifResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoseApp.class)
public class SinifResourceIntTest {

    private static final String DEFAULT_ADI = "AAAAAAAAAA";
    private static final String UPDATED_ADI = "BBBBBBBBBB";

    private static final String DEFAULT_ACIKLAMA = "AAAAAAAAAA";
    private static final String UPDATED_ACIKLAMA = "BBBBBBBBBB";

    @Autowired
    private SinifRepository sinifRepository;

    @Autowired
    private SinifService sinifService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSinifMockMvc;

    private Sinif sinif;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SinifResource sinifResource = new SinifResource(sinifService);
        this.restSinifMockMvc = MockMvcBuilders.standaloneSetup(sinifResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sinif createEntity(EntityManager em) {
        Sinif sinif = new Sinif()
            .adi(DEFAULT_ADI)
            .aciklama(DEFAULT_ACIKLAMA);
        return sinif;
    }

    @Before
    public void initTest() {
        sinif = createEntity(em);
    }

    @Test
    @Transactional
    public void createSinif() throws Exception {
        int databaseSizeBeforeCreate = sinifRepository.findAll().size();

        // Create the Sinif
        restSinifMockMvc.perform(post("/api/sinifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sinif)))
            .andExpect(status().isCreated());

        // Validate the Sinif in the database
        List<Sinif> sinifList = sinifRepository.findAll();
        assertThat(sinifList).hasSize(databaseSizeBeforeCreate + 1);
        Sinif testSinif = sinifList.get(sinifList.size() - 1);
        assertThat(testSinif.getAdi()).isEqualTo(DEFAULT_ADI);
        assertThat(testSinif.getAciklama()).isEqualTo(DEFAULT_ACIKLAMA);
    }

    @Test
    @Transactional
    public void createSinifWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sinifRepository.findAll().size();

        // Create the Sinif with an existing ID
        sinif.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSinifMockMvc.perform(post("/api/sinifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sinif)))
            .andExpect(status().isBadRequest());

        // Validate the Sinif in the database
        List<Sinif> sinifList = sinifRepository.findAll();
        assertThat(sinifList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSinifs() throws Exception {
        // Initialize the database
        sinifRepository.saveAndFlush(sinif);

        // Get all the sinifList
        restSinifMockMvc.perform(get("/api/sinifs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sinif.getId().intValue())))
            .andExpect(jsonPath("$.[*].adi").value(hasItem(DEFAULT_ADI.toString())))
            .andExpect(jsonPath("$.[*].aciklama").value(hasItem(DEFAULT_ACIKLAMA.toString())));
    }

    @Test
    @Transactional
    public void getSinif() throws Exception {
        // Initialize the database
        sinifRepository.saveAndFlush(sinif);

        // Get the sinif
        restSinifMockMvc.perform(get("/api/sinifs/{id}", sinif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sinif.getId().intValue()))
            .andExpect(jsonPath("$.adi").value(DEFAULT_ADI.toString()))
            .andExpect(jsonPath("$.aciklama").value(DEFAULT_ACIKLAMA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSinif() throws Exception {
        // Get the sinif
        restSinifMockMvc.perform(get("/api/sinifs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSinif() throws Exception {
        // Initialize the database
        sinifService.save(sinif);

        int databaseSizeBeforeUpdate = sinifRepository.findAll().size();

        // Update the sinif
        Sinif updatedSinif = sinifRepository.findOne(sinif.getId());
        updatedSinif
            .adi(UPDATED_ADI)
            .aciklama(UPDATED_ACIKLAMA);

        restSinifMockMvc.perform(put("/api/sinifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSinif)))
            .andExpect(status().isOk());

        // Validate the Sinif in the database
        List<Sinif> sinifList = sinifRepository.findAll();
        assertThat(sinifList).hasSize(databaseSizeBeforeUpdate);
        Sinif testSinif = sinifList.get(sinifList.size() - 1);
        assertThat(testSinif.getAdi()).isEqualTo(UPDATED_ADI);
        assertThat(testSinif.getAciklama()).isEqualTo(UPDATED_ACIKLAMA);
    }

    @Test
    @Transactional
    public void updateNonExistingSinif() throws Exception {
        int databaseSizeBeforeUpdate = sinifRepository.findAll().size();

        // Create the Sinif

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSinifMockMvc.perform(put("/api/sinifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sinif)))
            .andExpect(status().isCreated());

        // Validate the Sinif in the database
        List<Sinif> sinifList = sinifRepository.findAll();
        assertThat(sinifList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSinif() throws Exception {
        // Initialize the database
        sinifService.save(sinif);

        int databaseSizeBeforeDelete = sinifRepository.findAll().size();

        // Get the sinif
        restSinifMockMvc.perform(delete("/api/sinifs/{id}", sinif.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sinif> sinifList = sinifRepository.findAll();
        assertThat(sinifList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sinif.class);
        Sinif sinif1 = new Sinif();
        sinif1.setId(1L);
        Sinif sinif2 = new Sinif();
        sinif2.setId(sinif1.getId());
        assertThat(sinif1).isEqualTo(sinif2);
        sinif2.setId(2L);
        assertThat(sinif1).isNotEqualTo(sinif2);
        sinif1.setId(null);
        assertThat(sinif1).isNotEqualTo(sinif2);
    }
}
