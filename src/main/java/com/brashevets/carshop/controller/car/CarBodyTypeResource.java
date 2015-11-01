package com.brashevets.carshop.controller.car;

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
import com.brashevets.carshop.model.CarBodyType;
import com.brashevets.carshop.repository.CarBodyTypeRepository;

/**
 * REST controller for managing CarBodyType.
 */
@RestController
@RequestMapping("/api")
public class CarBodyTypeResource {

    private final Logger log = LoggerFactory.getLogger(CarBodyTypeResource.class);

    @Inject
    private CarBodyTypeRepository carBodyTypeRepository;

    /**
     * POST  /carBodyTypes -> Create a new carBodyType.
     */
    @RequestMapping(value = "/carBodyTypes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarBodyType> createCarBodyType(@Valid @RequestBody CarBodyType carBodyType) throws URISyntaxException {
        log.debug("REST request to save CarBodyType : {}", carBodyType);
        if (carBodyType.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new carBodyType cannot already have an ID").body(null);
        }
        CarBodyType result = carBodyTypeRepository.save(carBodyType);
        return ResponseEntity.created(new URI("/api/carBodyTypes/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("carBodyType", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /carBodyTypes -> Updates an existing carBodyType.
     */
    @RequestMapping(value = "/carBodyTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarBodyType> updateCarBodyType(@Valid @RequestBody CarBodyType carBodyType) throws URISyntaxException {
        log.debug("REST request to update CarBodyType : {}", carBodyType);
        if (carBodyType.getId() == null) {
            return createCarBodyType(carBodyType);
        }
        CarBodyType result = carBodyTypeRepository.save(carBodyType);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("carBodyType", carBodyType.getId().toString()))
                .body(result);
    }

    /**
     * GET  /carBodyTypes -> get all the carBodyTypes.
     */
    @RequestMapping(value = "/carBodyTypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CarBodyType>> getAllCarBodyTypes(Pageable pageable)
        throws URISyntaxException {
        Page<CarBodyType> page = carBodyTypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/carBodyTypes");
        return new ResponseEntity<List<CarBodyType>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /carBodyTypes/:id -> get the "id" carBodyType.
     */
    @RequestMapping(value = "/carBodyTypes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarBodyType> getCarBodyType(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get CarBodyType : {}", id);
        CarBodyType carBodyType = carBodyTypeRepository.findOne(id);
        if (carBodyType == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(carBodyType, HttpStatus.OK);
    }

    /**
     * DELETE  /carBodyTypes/:id -> delete the "id" carBodyType.
     */
    @RequestMapping(value = "/carBodyTypes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteCarBodyType(@PathVariable Long id) {
        log.debug("REST request to delete CarBodyType : {}", id);
        carBodyTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("carBodyType", id.toString())).build();
    }
}
