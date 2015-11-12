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
import com.brashevets.carshop.model.car.Model;
import com.brashevets.carshop.repository.CarModelRepository;

/**
 * REST controller for managing CarModel.
 */
@RestController
@RequestMapping("/api")
public class CarModelResource {

    private final Logger log = LoggerFactory.getLogger(CarModelResource.class);

    @Inject
    private CarModelRepository carModelRepository;

    /**
     * POST /carModels -> Create a new carModel.
     */
    @RequestMapping(value = "/carModels", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Model> createCarModel(@Valid @RequestBody Model carModel) throws URISyntaxException {
        log.debug("REST request to save CarModel : {}", carModel);
        if (carModel.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new carModel cannot already have an ID").body(null);
        }
        Model result = carModelRepository.save(carModel);
        return ResponseEntity.created(new URI("/api/carModels/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("carModel", result.getId().toString())).body(result);
    }

    /**
     * PUT /carModels -> Updates an existing carModel.
     */
    @RequestMapping(value = "/carModels", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Model> updateCarModel(@Valid @RequestBody Model carModel) throws URISyntaxException {
        log.debug("REST request to update CarModel : {}", carModel);
        if (carModel.getId() == null) {
            return createCarModel(carModel);
        }
        Model result = carModelRepository.save(carModel);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("carModel", carModel.getId().toString()))
                .body(result);
    }

    /**
     * GET /carModels -> get all the carModels.
     */
    @RequestMapping(value = "/carModels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Model>> getAllCarModels(Pageable pageable) throws URISyntaxException {
        Page<Model> page = carModelRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/carModels");
        return new ResponseEntity<List<Model>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /carModels/:id -> get the "id" carModel.
     */
    @RequestMapping(value = "/carModels/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Model> getCarModel(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get CarModel : {}", id);
        Model carModel = carModelRepository.findOne(id);
        if (carModel == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(carModel, HttpStatus.OK);
    }

    /**
     * DELETE /carModels/:id -> delete the "id" carModel.
     */
    @RequestMapping(value = "/carModels/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteCarModel(@PathVariable Long id) {
        log.debug("REST request to delete CarModel : {}", id);
        carModelRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("carModel", id.toString())).build();
    }
}
