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
import com.brashevets.carshop.model.car.Make;
import com.brashevets.carshop.repository.CarMakeRepository;

/**
 * REST controller for managing CarMake.
 */
@RestController
@RequestMapping("/api")
public class CarMakeResource {

    private final Logger log = LoggerFactory.getLogger(CarMakeResource.class);

    @Inject
    private CarMakeRepository carMakeRepository;

    /**
     * POST /carMakes -> Create a new carMake.
     */
    @RequestMapping(value = "/carMakes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Make> createCarMake(@Valid @RequestBody Make carMake) throws URISyntaxException {
        log.debug("REST request to save CarMake : {}", carMake);
        if (carMake.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new carMake cannot already have an ID").body(null);
        }
        Make result = carMakeRepository.save(carMake);
        return ResponseEntity.created(new URI("/api/carMakes/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("carMake", result.getId().toString())).body(result);
    }

    /**
     * PUT /carMakes -> Updates an existing carMake.
     */
    @RequestMapping(value = "/carMakes", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Make> updateCarMake(@Valid @RequestBody Make carMake) throws URISyntaxException {
        log.debug("REST request to update CarMake : {}", carMake);
        if (carMake.getId() == null) {
            return createCarMake(carMake);
        }
        Make result = carMakeRepository.save(carMake);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("carMake", carMake.getId().toString()))
                .body(result);
    }

    /**
     * GET /carMakes -> get all the carMakes.
     */
    @RequestMapping(value = "/carMakes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Make>> getAllCarMakes(Pageable pageable) throws URISyntaxException {
        Page<Make> page = carMakeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/carMakes");
        return new ResponseEntity<List<Make>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /carMakes/:id -> get the "id" carMake.
     */
    @RequestMapping(value = "/carMakes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Make> getCarMake(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get CarMake : {}", id);
        Make carMake = carMakeRepository.findOne(id);
        if (carMake == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(carMake, HttpStatus.OK);
    }

    /**
     * DELETE /carMakes/:id -> delete the "id" carMake.
     */
    @RequestMapping(value = "/carMakes/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteCarMake(@PathVariable Long id) {
        log.debug("REST request to delete CarMake : {}", id);
        carMakeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("carMake", id.toString())).build();
    }
}
