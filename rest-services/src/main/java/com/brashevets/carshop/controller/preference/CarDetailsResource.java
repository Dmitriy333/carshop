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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.brashevets.carshop.controller.util.HeaderUtil;
import com.brashevets.carshop.controller.util.PaginationUtil;
import com.brashevets.carshop.model.car.CarDetails;
import com.brashevets.carshop.repository.CarDetailsRepository;

/**
 * REST controller for managing CarDetails.
 */
@RestController
@RequestMapping("/api")
public class CarDetailsResource {

    private final Logger log = LoggerFactory.getLogger(CarDetailsResource.class);

    @Inject
    private CarDetailsRepository carDetailsRepository;

    /**
     * POST /carDetailss -> Create a new carDetails.
     */
    @RequestMapping(value = "/carDetailss", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarDetails> createCarDetails(@Valid @RequestBody CarDetails carDetails)
            throws URISyntaxException {
        log.debug("REST request to save CarDetails : {}", carDetails);
        if (carDetails.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new carDetails cannot already have an ID")
                    .body(null);
        }
        CarDetails result = carDetailsRepository.save(carDetails);
        return ResponseEntity.created(new URI("/api/carDetailss/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("carDetails", result.getId().toString())).body(result);
    }

    /**
     * PUT /carDetailss -> Updates an existing carDetails.
     */
    @RequestMapping(value = "/carDetailss", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarDetails> updateCarDetails(@Valid @RequestBody CarDetails carDetails)
            throws URISyntaxException {
        log.debug("REST request to update CarDetails : {}", carDetails);
        if (carDetails.getId() == null) {
            return createCarDetails(carDetails);
        }
        CarDetails result = carDetailsRepository.save(carDetails);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("carDetails", carDetails.getId().toString())).body(result);
    }

    /**
     * GET /carDetailss -> get all the carDetailss.
     */
    @RequestMapping(value = "/carDetailss", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CarDetails>> getAllCarDetailss(Pageable pageable) throws URISyntaxException {
        Page<CarDetails> page = carDetailsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/carDetailss");
        return new ResponseEntity<List<CarDetails>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /carDetailss/:id -> get the "id" carDetails.
     */
    @RequestMapping(value = "/carDetailss/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarDetails> getCarDetails(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get CarDetails : {}", id);
        CarDetails carDetails = carDetailsRepository.findOne(id);
        if (carDetails == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(carDetails, HttpStatus.OK);
    }

    /**
     * DELETE /carDetailss/:id -> delete the "id" carDetails.
     */
    @RequestMapping(value = "/carDetailss/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteCarDetails(@PathVariable Long id) {
        log.debug("REST request to delete CarDetails : {}", id);
        carDetailsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("carDetails", id.toString())).build();
    }
}
