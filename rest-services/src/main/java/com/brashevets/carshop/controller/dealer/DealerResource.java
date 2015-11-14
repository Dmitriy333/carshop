package com.brashevets.carshop.controller.dealer;

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
import com.brashevets.carshop.model.dealer.Dealer;
import com.brashevets.carshop.repository.DealerRepository;

/**
 * REST controller for managing Dealer.
 */
@RestController
@RequestMapping("/api")
public class DealerResource {

    private final Logger log = LoggerFactory.getLogger(DealerResource.class);

    @Inject
    private DealerRepository dealerRepository;

    /**
     * POST /dealers -> Create a new dealer.
     */
    @RequestMapping(value = "/dealers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dealer> createDealer(@Valid @RequestBody Dealer dealer) throws URISyntaxException {
        log.debug("REST request to save Dealer : {}", dealer);
        if (dealer.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dealer cannot already have an ID").body(null);
        }
        Dealer result = dealerRepository.save(dealer);
        return ResponseEntity.created(new URI("/api/dealers/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("dealer", result.getId().toString())).body(result);
    }

    /**
     * PUT /dealers -> Updates an existing dealer.
     */
    @RequestMapping(value = "/dealers", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dealer> updateDealer(@Valid @RequestBody Dealer dealer) throws URISyntaxException {
        log.debug("REST request to update Dealer : {}", dealer);
        if (dealer.getId() == null) {
            return createDealer(dealer);
        }
        Dealer result = dealerRepository.save(dealer);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("dealer", dealer.getId().toString()))
                .body(result);
    }

    /**
     * GET /dealers -> get all the dealers.
     */
    @RequestMapping(value = "/dealers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Dealer>> getAllDealers(Pageable pageable) throws URISyntaxException {
        Page<Dealer> page = dealerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dealers");
        return new ResponseEntity<List<Dealer>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /dealers/:id -> get the "id" dealer.
     */
    @RequestMapping(value = "/dealers/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dealer> getDealer(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Dealer : {}", id);
        Dealer dealer = dealerRepository.findOne(id);
        if (dealer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dealer, HttpStatus.OK);
    }

    /**
     * DELETE /dealers/:id -> delete the "id" dealer.
     */
    @RequestMapping(value = "/dealers/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteDealer(@PathVariable Long id) {
        log.debug("REST request to delete Dealer : {}", id);
        dealerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dealer", id.toString())).build();
    }
}
