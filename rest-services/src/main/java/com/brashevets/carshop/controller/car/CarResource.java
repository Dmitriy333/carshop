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

import com.brashevets.carshop.controller.PathMapping;
import com.brashevets.carshop.controller.util.HeaderUtil;
import com.brashevets.carshop.controller.util.PaginationUtil;
import com.brashevets.carshop.model.car.Car;
import com.brashevets.carshop.repository.CarRepository;

/**
 * REST controller for managing Car.
 */
@RestController
@RequestMapping("/cars")
public class CarResource {

    private final Logger log = LoggerFactory.getLogger(CarResource.class);

    @Inject
    private CarRepository carRepository;

    /**
     * POST /cars -> Create a new car.
     */
    @RequestMapping(value = PathMapping.DEFAULT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Car> createCar(@Valid @RequestBody Car car) throws URISyntaxException {
        log.debug("REST request to save Car : {}", car);
        if (car.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new car cannot already have an ID").body(null);
        }
        Car result = carRepository.save(car);
        return ResponseEntity.created(new URI("/api/cars/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("car", result.getId().toString())).body(result);
    }

    /**
     * PUT /cars -> Updates an existing car.
     */
    @RequestMapping(value = PathMapping.DEFAULT, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Car> updateCar(@Valid @RequestBody Car car) throws URISyntaxException {
        log.debug("REST request to update Car : {}", car);
        if (car.getId() == null) {
            return createCar(car);
        }
        Car result = carRepository.save(car);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("car", car.getId().toString()))
                .body(result);
    }

    /**
     * GET /cars -> get all the cars.
     */
    @RequestMapping(value = PathMapping.DEFAULT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Car>> getAllCars(Pageable pageable) throws URISyntaxException {
        Page<Car> page = carRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cars");
        return new ResponseEntity<List<Car>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /cars/:id -> get the "id" car.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Car> getCar(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Car : {}", id);
        Car car = carRepository.findOne(id);
        if (car == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    /**
     * DELETE /cars/:id -> delete the "id" car.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        log.debug("REST request to delete Car : {}", id);
        carRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("car", id.toString())).build();
    }
}
