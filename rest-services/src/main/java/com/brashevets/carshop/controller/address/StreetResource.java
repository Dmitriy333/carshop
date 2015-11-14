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
import com.brashevets.carshop.model.address.Street;
import com.brashevets.carshop.repository.StreetRepository;

/**
 * REST controller for managing Street.
 */
@RestController
@RequestMapping("/api")
public class StreetResource {

    private final Logger log = LoggerFactory.getLogger(StreetResource.class);

    @Inject
    private StreetRepository streetRepository;

    /**
     * POST /streets -> Create a new street.
     */
    @RequestMapping(value = "/streets", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Street> createStreet(@Valid @RequestBody Street street) throws URISyntaxException {
        log.debug("REST request to save Street : {}", street);
        if (street.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new street cannot already have an ID").body(null);
        }
        Street result = streetRepository.save(street);
        return ResponseEntity.created(new URI("/api/streets/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("street", result.getId().toString())).body(result);
    }

    /**
     * PUT /streets -> Updates an existing street.
     */
    @RequestMapping(value = "/streets", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Street> updateStreet(@Valid @RequestBody Street street) throws URISyntaxException {
        log.debug("REST request to update Street : {}", street);
        if (street.getId() == null) {
            return createStreet(street);
        }
        Street result = streetRepository.save(street);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("street", street.getId().toString()))
                .body(result);
    }

    /**
     * GET /streets -> get all the streets.
     */
    @RequestMapping(value = "/streets", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Street>> getAllStreets(Pageable pageable) throws URISyntaxException {
        Page<Street> page = streetRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/streets");
        return new ResponseEntity<List<Street>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /streets/:id -> get the "id" street.
     */
    @RequestMapping(value = "/streets/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Street> getStreet(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Street : {}", id);
        Street street = streetRepository.findOne(id);
        if (street == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(street, HttpStatus.OK);
    }

    /**
     * DELETE /streets/:id -> delete the "id" street.
     */
    @RequestMapping(value = "/streets/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteStreet(@PathVariable Long id) {
        log.debug("REST request to delete Street : {}", id);
        streetRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("street", id.toString())).build();
    }
}
