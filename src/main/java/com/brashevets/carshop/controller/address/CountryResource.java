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
import org.springframework.test.annotation.Timed;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.brashevets.carshop.controller.PathMapping;
import com.brashevets.carshop.controller.util.HeaderUtil;
import com.brashevets.carshop.controller.util.PaginationUtil;
import com.brashevets.carshop.model.Country;
import com.brashevets.carshop.repository.CountryRepository;

/**
 * REST controller for managing Country.
 */
@RestController
@RequestMapping("/countries")
public class CountryResource {

    private final Logger log = LoggerFactory.getLogger(CountryResource.class);

    @Inject
    private CountryRepository countryRepository;

    /**
     * POST /countrys -> Create a new country.
     */
    @RequestMapping(value = PathMapping.DEFAULT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Country> createCountry(@Valid @RequestBody Country country) throws URISyntaxException {
        log.debug("REST request to save Country : {}", country);
        if (country.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new country cannot already have an ID").body(null);
        }
        Country result = countryRepository.save(country);
        return ResponseEntity.created(new URI("/api/countrys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("country", result.getId().toString())).body(result);
    }

    /**
     * PUT /countrys -> Updates an existing country.
     */
    @RequestMapping(value = PathMapping.DEFAULT, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Country> updateCountry(@Valid @RequestBody Country country) throws URISyntaxException {
        log.debug("REST request to update Country : {}", country);
        if (country.getId() == null) {
            return createCountry(country);
        }
        Country result = countryRepository.save(country);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("country", country.getId().toString()))
                .body(result);
    }

    /**
     * GET /countrys -> get all the countrys.
     */
    @RequestMapping(value = PathMapping.DEFAULT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Country>> getAllCountrys(Pageable pageable) throws URISyntaxException {
        Page<Country> page = countryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/countrys");
        return new ResponseEntity<List<Country>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /countrys/:id -> get the "id" country.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Country> getCountry(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Country : {}", id);
        Country country = countryRepository.findOne(id);
        if (country == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    /**
     * DELETE /countrys/:id -> delete the "id" country.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        log.debug("REST request to delete Country : {}", id);
        countryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("country", id.toString())).build();
    }
}
