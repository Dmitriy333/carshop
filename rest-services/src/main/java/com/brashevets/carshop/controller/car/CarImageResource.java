package com.brashevets.carshop.controller.car;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

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
import com.brashevets.carshop.model.image.CarImage;
import com.brashevets.carshop.repository.CarImageRepository;

/**
 * REST controller for managing CarImage.
 */
@RestController
@RequestMapping("/api")
public class CarImageResource {

    private final Logger log = LoggerFactory.getLogger(CarImageResource.class);

    @Inject
    private CarImageRepository carImageRepository;

    /**
     * POST /carImages -> Create a new carImage.
     */
    @RequestMapping(value = "/carImages", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarImage> createCarImage(@RequestBody CarImage carImage) throws URISyntaxException {
        log.debug("REST request to save CarImage : {}", carImage);
        if (carImage.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new carImage cannot already have an ID").body(null);
        }
        CarImage result = carImageRepository.save(carImage);
        return ResponseEntity.created(new URI("/api/carImages/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("carImage", result.getId().toString())).body(result);
    }

    /**
     * PUT /carImages -> Updates an existing carImage.
     */
    @RequestMapping(value = "/carImages", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarImage> updateCarImage(@RequestBody CarImage carImage) throws URISyntaxException {
        log.debug("REST request to update CarImage : {}", carImage);
        if (carImage.getId() == null) {
            return createCarImage(carImage);
        }
        CarImage result = carImageRepository.save(carImage);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("carImage", carImage.getId().toString()))
                .body(result);
    }

    /**
     * GET /carImages -> get all the carImages.
     */
    @RequestMapping(value = "/carImages", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CarImage>> getAllCarImages(Pageable pageable) throws URISyntaxException {
        Page<CarImage> page = carImageRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/carImages");
        return new ResponseEntity<List<CarImage>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /carImages/:id -> get the "id" carImage.
     */
    @RequestMapping(value = "/carImages/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarImage> getCarImage(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get CarImage : {}", id);
        CarImage carImage = carImageRepository.findOne(id);
        if (carImage == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(carImage, HttpStatus.OK);
    }

    /**
     * DELETE /carImages/:id -> delete the "id" carImage.
     */
    @RequestMapping(value = "/carImages/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteCarImage(@PathVariable Long id) {
        log.debug("REST request to delete CarImage : {}", id);
        carImageRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("carImage", id.toString())).build();
    }
}
