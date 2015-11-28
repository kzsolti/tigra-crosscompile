package hu.tigra.crosscompile.web.rest;

import hu.tigra.crosscompile.Application;
import hu.tigra.crosscompile.domain.Ugylet;
import hu.tigra.crosscompile.repository.UgyletRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the UgyletResource REST controller.
 *
 * @see UgyletResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UgyletResourceIntTest {

    private static final String DEFAULT_UGYLETSZAM = "AAAAA";
    private static final String UPDATED_UGYLETSZAM = "BBBBB";
    private static final String DEFAULT_UGYFEL = "AAAAA";
    private static final String UPDATED_UGYFEL = "BBBBB";
    private static final String DEFAULT_TAJSZAM = "AAAAA";
    private static final String UPDATED_TAJSZAM = "BBBBB";

    private static final LocalDate DEFAULT_BEERKEZES_IDEJE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BEERKEZES_IDEJE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_MELLEKLET_SZAM = 1;
    private static final Integer UPDATED_MELLEKLET_SZAM = 2;

    @Inject
    private UgyletRepository ugyletRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUgyletMockMvc;

    private Ugylet ugylet;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UgyletResource ugyletResource = new UgyletResource();
        ReflectionTestUtils.setField(ugyletResource, "ugyletRepository", ugyletRepository);
        this.restUgyletMockMvc = MockMvcBuilders.standaloneSetup(ugyletResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        ugylet = new Ugylet();
        ugylet.setUgyletszam(DEFAULT_UGYLETSZAM);
        ugylet.setUgyfel(DEFAULT_UGYFEL);
        ugylet.setTajszam(DEFAULT_TAJSZAM);
        ugylet.setBeerkezesIdeje(DEFAULT_BEERKEZES_IDEJE);
        ugylet.setMellekletSzam(DEFAULT_MELLEKLET_SZAM);
    }

    @Test
    @Transactional
    public void createUgylet() throws Exception {
        int databaseSizeBeforeCreate = ugyletRepository.findAll().size();

        // Create the Ugylet

        restUgyletMockMvc.perform(post("/api/ugylets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ugylet)))
                .andExpect(status().isCreated());

        // Validate the Ugylet in the database
        List<Ugylet> ugylets = ugyletRepository.findAll();
        assertThat(ugylets).hasSize(databaseSizeBeforeCreate + 1);
        Ugylet testUgylet = ugylets.get(ugylets.size() - 1);
        assertThat(testUgylet.getUgyletszam()).isEqualTo(DEFAULT_UGYLETSZAM);
        assertThat(testUgylet.getUgyfel()).isEqualTo(DEFAULT_UGYFEL);
        assertThat(testUgylet.getTajszam()).isEqualTo(DEFAULT_TAJSZAM);
        assertThat(testUgylet.getBeerkezesIdeje()).isEqualTo(DEFAULT_BEERKEZES_IDEJE);
        assertThat(testUgylet.getMellekletSzam()).isEqualTo(DEFAULT_MELLEKLET_SZAM);
    }

    @Test
    @Transactional
    public void getAllUgylets() throws Exception {
        // Initialize the database
        ugyletRepository.saveAndFlush(ugylet);

        // Get all the ugylets
        restUgyletMockMvc.perform(get("/api/ugylets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ugylet.getId().intValue())))
                .andExpect(jsonPath("$.[*].ugyletszam").value(hasItem(DEFAULT_UGYLETSZAM.toString())))
                .andExpect(jsonPath("$.[*].ugyfel").value(hasItem(DEFAULT_UGYFEL.toString())))
                .andExpect(jsonPath("$.[*].tajszam").value(hasItem(DEFAULT_TAJSZAM.toString())))
                .andExpect(jsonPath("$.[*].beerkezesIdeje").value(hasItem(DEFAULT_BEERKEZES_IDEJE.toString())))
                .andExpect(jsonPath("$.[*].mellekletSzam").value(hasItem(DEFAULT_MELLEKLET_SZAM)));
    }

    @Test
    @Transactional
    public void getUgylet() throws Exception {
        // Initialize the database
        ugyletRepository.saveAndFlush(ugylet);

        // Get the ugylet
        restUgyletMockMvc.perform(get("/api/ugylets/{id}", ugylet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ugylet.getId().intValue()))
            .andExpect(jsonPath("$.ugyletszam").value(DEFAULT_UGYLETSZAM.toString()))
            .andExpect(jsonPath("$.ugyfel").value(DEFAULT_UGYFEL.toString()))
            .andExpect(jsonPath("$.tajszam").value(DEFAULT_TAJSZAM.toString()))
            .andExpect(jsonPath("$.beerkezesIdeje").value(DEFAULT_BEERKEZES_IDEJE.toString()))
            .andExpect(jsonPath("$.mellekletSzam").value(DEFAULT_MELLEKLET_SZAM));
    }

    @Test
    @Transactional
    public void getNonExistingUgylet() throws Exception {
        // Get the ugylet
        restUgyletMockMvc.perform(get("/api/ugylets/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUgylet() throws Exception {
        // Initialize the database
        ugyletRepository.saveAndFlush(ugylet);

		int databaseSizeBeforeUpdate = ugyletRepository.findAll().size();

        // Update the ugylet
        ugylet.setUgyletszam(UPDATED_UGYLETSZAM);
        ugylet.setUgyfel(UPDATED_UGYFEL);
        ugylet.setTajszam(UPDATED_TAJSZAM);
        ugylet.setBeerkezesIdeje(UPDATED_BEERKEZES_IDEJE);
        ugylet.setMellekletSzam(UPDATED_MELLEKLET_SZAM);

        restUgyletMockMvc.perform(put("/api/ugylets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ugylet)))
                .andExpect(status().isOk());

        // Validate the Ugylet in the database
        List<Ugylet> ugylets = ugyletRepository.findAll();
        assertThat(ugylets).hasSize(databaseSizeBeforeUpdate);
        Ugylet testUgylet = ugylets.get(ugylets.size() - 1);
        assertThat(testUgylet.getUgyletszam()).isEqualTo(UPDATED_UGYLETSZAM);
        assertThat(testUgylet.getUgyfel()).isEqualTo(UPDATED_UGYFEL);
        assertThat(testUgylet.getTajszam()).isEqualTo(UPDATED_TAJSZAM);
        assertThat(testUgylet.getBeerkezesIdeje()).isEqualTo(UPDATED_BEERKEZES_IDEJE);
        assertThat(testUgylet.getMellekletSzam()).isEqualTo(UPDATED_MELLEKLET_SZAM);
    }

    @Test
    @Transactional
    public void deleteUgylet() throws Exception {
        // Initialize the database
        ugyletRepository.saveAndFlush(ugylet);

		int databaseSizeBeforeDelete = ugyletRepository.findAll().size();

        // Get the ugylet
        restUgyletMockMvc.perform(delete("/api/ugylets/{id}", ugylet.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Ugylet> ugylets = ugyletRepository.findAll();
        assertThat(ugylets).hasSize(databaseSizeBeforeDelete - 1);
    }
}
