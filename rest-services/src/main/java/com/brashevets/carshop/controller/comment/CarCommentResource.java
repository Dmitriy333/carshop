package com.brashevets.carshop.controller.comment;

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
import org.springframework.test.annotation.Timed;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.brashevets.carshop.controller.util.HeaderUtil;
import com.brashevets.carshop.controller.util.PaginationUtil;
import com.brashevets.carshop.model.comment.CarComment;
import com.brashevets.carshop.repository.CarCommentRepository;

/**
 * REST controller for managing CarComment.
 */
@RestController
@RequestMapping("/api")
public class CarCommentResource {

    private final Logger log = LoggerFactory.getLogger(CarCommentResource.class);

    @Inject
    private CarCommentRepository carCommentRepository;

    /**
     * POST  /carComments -> Create a new carComment.
     */
    @RequestMapping(value = "/carComments",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarComment> createCarComment(@RequestBody CarComment carComment) throws URISyntaxException {
        log.debug("REST request to save CarComment : {}", carComment);
        if (carComment.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new carComment cannot already have an ID").body(null);
        }
        CarComment result = carCommentRepository.save(carComment);
        return ResponseEntity.created(new URI("/api/carComments/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("carComment", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /carComments -> Updates an existing carComment.
     */
    @RequestMapping(value = "/carComments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarComment> updateCarComment(@RequestBody CarComment carComment) throws URISyntaxException {
        log.debug("REST request to update CarComment : {}", carComment);
        if (carComment.getId() == null) {
            return createCarComment(carComment);
        }
        CarComment result = carCommentRepository.save(carComment);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("carComment", carComment.getId().toString()))
                .body(result);
    }

    /**
     * GET  /carComments -> get all the carComments.
     */
    @RequestMapping(value = "/carComments",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CarComment>> getAllCarComments(Pageable pageable)
        throws URISyntaxException {
        Page<CarComment> page = carCommentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/carComments");
        return new ResponseEntity<List<CarComment>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /carComments/:id -> get the "id" carComment.
     */
    @RequestMapping(value = "/carComments/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarComment> getCarComment(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get CarComment : {}", id);
        CarComment carComment = carCommentRepository.findOne(id);
        if (carComment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(carComment, HttpStatus.OK);
    }

    /**
     * DELETE  /carComments/:id -> delete the "id" carComment.
     */
    @RequestMapping(value = "/carComments/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteCarComment(@PathVariable Long id) {
        log.debug("REST request to delete CarComment : {}", id);
        carCommentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("carComment", id.toString())).build();
    }
}
