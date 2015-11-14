package com.brashevets.carshop.controller.comment;

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
import com.brashevets.carshop.model.comment.DealerFeedback;
import com.brashevets.carshop.repository.DealerFeedbackRepository;

/**
 * REST controller for managing DealerFeedback.
 */
@RestController
@RequestMapping("/api")
public class DealerFeedbackResource {

    private final Logger log = LoggerFactory.getLogger(DealerFeedbackResource.class);

    @Inject
    private DealerFeedbackRepository dealerFeedbackRepository;

    /**
     * POST /dealerFeedbacks -> Create a new dealerFeedback.
     */
    @RequestMapping(value = "/dealerFeedbacks", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DealerFeedback> createDealerFeedback(@Valid @RequestBody DealerFeedback dealerFeedback)
            throws URISyntaxException {
        log.debug("REST request to save DealerFeedback : {}", dealerFeedback);
        if (dealerFeedback.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dealerFeedback cannot already have an ID")
                    .body(null);
        }
        DealerFeedback result = dealerFeedbackRepository.save(dealerFeedback);
        return ResponseEntity.created(new URI("/api/dealerFeedbacks/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("dealerFeedback", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /dealerFeedbacks -> Updates an existing dealerFeedback.
     */
    @RequestMapping(value = "/dealerFeedbacks", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DealerFeedback> updateDealerFeedback(@Valid @RequestBody DealerFeedback dealerFeedback)
            throws URISyntaxException {
        log.debug("REST request to update DealerFeedback : {}", dealerFeedback);
        if (dealerFeedback.getId() == null) {
            return createDealerFeedback(dealerFeedback);
        }
        DealerFeedback result = dealerFeedbackRepository.save(dealerFeedback);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("dealerFeedback", dealerFeedback.getId().toString()))
                .body(result);
    }

    /**
     * GET /dealerFeedbacks -> get all the dealerFeedbacks.
     */
    @RequestMapping(value = "/dealerFeedbacks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DealerFeedback>> getAllDealerFeedbacks(Pageable pageable) throws URISyntaxException {
        Page<DealerFeedback> page = dealerFeedbackRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dealerFeedbacks");
        return new ResponseEntity<List<DealerFeedback>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /dealerFeedbacks/:id -> get the "id" dealerFeedback.
     */
    @RequestMapping(value = "/dealerFeedbacks/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DealerFeedback> getDealerFeedback(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get DealerFeedback : {}", id);
        DealerFeedback dealerFeedback = dealerFeedbackRepository.findOne(id);
        if (dealerFeedback == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dealerFeedback, HttpStatus.OK);
    }

    /**
     * DELETE /dealerFeedbacks/:id -> delete the "id" dealerFeedback.
     */
    @RequestMapping(value = "/dealerFeedbacks/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteDealerFeedback(@PathVariable Long id) {
        log.debug("REST request to delete DealerFeedback : {}", id);
        dealerFeedbackRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dealerFeedback", id.toString()))
                .build();
    }
}
