package com.brashevets.carshop.controller.preference;

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

import com.brashevets.carshop.controller.util.HeaderUtil;
import com.brashevets.carshop.controller.util.PaginationUtil;
import com.brashevets.carshop.model.DefaultCarMeta;
import com.brashevets.carshop.repository.DefaultCarMetaRepository;

/**
 * REST controller for managing DefaultCarMeta.
 */
@RestController
@RequestMapping("/api")
public class DefaultCarMetaResource {

    private final Logger log = LoggerFactory.getLogger(DefaultCarMetaResource.class);

    @Inject
    private DefaultCarMetaRepository defaultCarMetaRepository;

    /**
     * POST /defaultCarMetas -> Create a new defaultCarMeta.
     */
    @RequestMapping(value = "/defaultCarMetas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultCarMeta> createDefaultCarMeta(@Valid @RequestBody DefaultCarMeta defaultCarMeta)
            throws URISyntaxException {
        log.debug("REST request to save DefaultCarMeta : {}", defaultCarMeta);
        if (defaultCarMeta.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new defaultCarMeta cannot already have an ID")
                    .body(null);
        }
        DefaultCarMeta result = defaultCarMetaRepository.save(defaultCarMeta);
        return ResponseEntity.created(new URI("/api/defaultCarMetas/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("defaultCarMeta", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /defaultCarMetas -> Updates an existing defaultCarMeta.
     */
    @RequestMapping(value = "/defaultCarMetas", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultCarMeta> updateDefaultCarMeta(@Valid @RequestBody DefaultCarMeta defaultCarMeta)
            throws URISyntaxException {
        log.debug("REST request to update DefaultCarMeta : {}", defaultCarMeta);
        if (defaultCarMeta.getId() == null) {
            return createDefaultCarMeta(defaultCarMeta);
        }
        DefaultCarMeta result = defaultCarMetaRepository.save(defaultCarMeta);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("defaultCarMeta", defaultCarMeta.getId().toString()))
                .body(result);
    }

    /**
     * GET /defaultCarMetas -> get all the defaultCarMetas.
     */
    @RequestMapping(value = "/defaultCarMetas", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DefaultCarMeta>> getAllDefaultCarMetas(Pageable pageable) throws URISyntaxException {
        Page<DefaultCarMeta> page = defaultCarMetaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/defaultCarMetas");
        return new ResponseEntity<List<DefaultCarMeta>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /defaultCarMetas/:id -> get the "id" defaultCarMeta.
     */
    @RequestMapping(value = "/defaultCarMetas/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultCarMeta> getDefaultCarMeta(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get DefaultCarMeta : {}", id);
        DefaultCarMeta defaultCarMeta = defaultCarMetaRepository.findOne(id);
        if (defaultCarMeta == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(defaultCarMeta, HttpStatus.OK);
    }

    /**
     * DELETE /defaultCarMetas/:id -> delete the "id" defaultCarMeta.
     */
    @RequestMapping(value = "/defaultCarMetas/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteDefaultCarMeta(@PathVariable Long id) {
        log.debug("REST request to delete DefaultCarMeta : {}", id);
        defaultCarMetaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("defaultCarMeta", id.toString()))
                .build();
    }
}
