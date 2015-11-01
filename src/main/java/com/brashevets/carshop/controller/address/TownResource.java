package com.brashevets.carshop.controller.address;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.brashevets.carshop.controller.util.HeaderUtil;
import com.brashevets.carshop.controller.util.PaginationUtil;
import com.brashevets.carshop.model.Town;
import com.brashevets.carshop.repository.TownRepository;

/**
 * REST controller for managing Town.
 */
@RestController
@RequestMapping("/api")
public class TownResource {

    private final Logger log = LoggerFactory.getLogger(TownResource.class);

    @Inject
    private TownRepository townRepository;

    /**
     * POST /towns -> Create a new town.
     */
    @RequestMapping(value = "/towns", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Town> createTown(@Valid @RequestBody Town town) throws URISyntaxException {
        log.debug("REST request to save Town : {}", town);
        if (town.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new town cannot already have an ID").body(null);
        }
        Town result = townRepository.save(town);
        return ResponseEntity.created(new URI("/api/towns/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("town", result.getId().toString())).body(result);
    }

    /**
     * PUT /towns -> Updates an existing town.
     */
    @RequestMapping(value = "/towns", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Town> updateTown(@Valid @RequestBody Town town) throws URISyntaxException {
        log.debug("REST request to update Town : {}", town);
        if (town.getId() == null) {
            return createTown(town);
        }
        Town result = townRepository.save(town);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("town", town.getId().toString()))
                .body(result);
    }

    /**
     * GET /towns -> get all the towns.
     */
    @RequestMapping(value = "/towns", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Town>> getAllTowns(Pageable pageable) throws URISyntaxException {
        Page<Town> page = townRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/towns");
        return new ResponseEntity<List<Town>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /towns/:id -> get the "id" town.
     */
    @RequestMapping(value = "/towns/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Town> getTown(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Town : {}", id);
        Town town = townRepository.findOne(id);
        if (town == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(town, HttpStatus.OK);
    }

    /**
     * DELETE /towns/:id -> delete the "id" town.
     */
    @RequestMapping(value = "/towns/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteTown(@PathVariable Long id) {
        log.debug("REST request to delete Town : {}", id);
        townRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("town", id.toString())).build();
    }
}
