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

import com.brashevets.carshop.controller.PathMapping;
import com.brashevets.carshop.controller.util.HeaderUtil;
import com.brashevets.carshop.controller.util.PaginationUtil;
import com.brashevets.carshop.model.Address;
import com.brashevets.carshop.repository.AddressRepository;

/**
 * REST controller for managing Address.
 */
@RestController
@RequestMapping("/address")
public class AddressResource {

    private final Logger log = LoggerFactory.getLogger(AddressResource.class);

    @Inject
    private AddressRepository addressRepository;

    /**
     * POST /addresss -> Create a new address.
     */
    @RequestMapping(value = PathMapping.DEFAULT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Address> createAddress(@Valid @RequestBody Address address) throws URISyntaxException {
        log.debug("REST request to save Address : {}", address);
        if (address.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new address cannot already have an ID").body(null);
        }
        Address result = addressRepository.save(address);
        return ResponseEntity.created(new URI("/api/addresss/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("address", result.getId().toString())).body(result);
    }

    /**
     * PUT /addresss -> Updates an existing address.
     */
    @RequestMapping(value = "/addresss", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Address> updateAddress(@Valid @RequestBody Address address) throws URISyntaxException {
        log.debug("REST request to update Address : {}", address);
        if (address.getId() == null) {
            return createAddress(address);
        }
        Address result = addressRepository.save(address);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("address", address.getId().toString()))
                .body(result);
    }

    /**
     * GET /addresss -> get all the addresss.
     */
    @RequestMapping(value = "/addresss", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Address>> getAllAddresss(Pageable pageable) throws URISyntaxException {
        Page<Address> page = addressRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/addresss");
        return new ResponseEntity<List<Address>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /addresss/:id -> get the "id" address.
     */
    @RequestMapping(value = "/addresss/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Address> getAddress(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Address : {}", id);
        Address address = addressRepository.findOne(id);
        if (address == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    /**
     * DELETE /addresss/:id -> delete the "id" address.
     */
    @RequestMapping(value = "/addresss/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        log.debug("REST request to delete Address : {}", id);
        addressRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("address", id.toString())).build();
    }
}
