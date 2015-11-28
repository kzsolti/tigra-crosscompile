package hu.tigra.crosscompile.web.rest;

import com.codahale.metrics.annotation.Timed;
import hu.tigra.crosscompile.domain.Ugylet;
import hu.tigra.crosscompile.repository.UgyletRepository;
import hu.tigra.crosscompile.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Ugylet.
 */
@RestController
@RequestMapping("/api")
public class UgyletResource {

    private final Logger log = LoggerFactory.getLogger(UgyletResource.class);

    @Inject
    private UgyletRepository ugyletRepository;

    /**
     * POST  /ugylets -> Create a new ugylet.
     */
    @RequestMapping(value = "/ugylets",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ugylet> createUgylet(@RequestBody Ugylet ugylet) throws URISyntaxException {
        log.debug("REST request to save Ugylet : {}", ugylet);
        if (ugylet.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new ugylet cannot already have an ID").body(null);
        }
        Ugylet result = ugyletRepository.save(ugylet);
        return ResponseEntity.created(new URI("/api/ugylets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ugylet", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ugylets -> Updates an existing ugylet.
     */
    @RequestMapping(value = "/ugylets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ugylet> updateUgylet(@RequestBody Ugylet ugylet) throws URISyntaxException {
        log.debug("REST request to update Ugylet : {}", ugylet);
        if (ugylet.getId() == null) {
            return createUgylet(ugylet);
        }
        Ugylet result = ugyletRepository.save(ugylet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("ugylet", ugylet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ugylets -> get all the ugylets.
     */
    @RequestMapping(value = "/ugylets",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Ugylet> getAllUgylets() {
        log.debug("REST request to get all Ugylets");
        return ugyletRepository.findAll();
    }

    /**
     * GET  /ugylets/:id -> get the "id" ugylet.
     */
    @RequestMapping(value = "/ugylets/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ugylet> getUgylet(@PathVariable Long id) {
        log.debug("REST request to get Ugylet : {}", id);
        return Optional.ofNullable(ugyletRepository.findOne(id))
            .map(ugylet -> new ResponseEntity<>(
                ugylet,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ugylets/:id -> delete the "id" ugylet.
     */
    @RequestMapping(value = "/ugylets/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUgylet(@PathVariable Long id) {
        log.debug("REST request to delete Ugylet : {}", id);
        ugyletRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ugylet", id.toString())).build();
    }
}
