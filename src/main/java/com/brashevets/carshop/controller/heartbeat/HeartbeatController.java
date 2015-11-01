package com.brashevets.carshop.controller.heartbeat;

import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brashevets.carshop.controller.PathMapping;

/**
 * Controller to check services availability.
 *
 */
@Controller
@RequestMapping("/")
public class HeartbeatController {

    protected static final String NO_CACHE = "no-cache";

    /**
     * Helps to check whether the system is alive. Body contains current system
     * time in milliseconds.
     */
    @RequestMapping(value = PathMapping.DEFAULT, method = RequestMethod.GET)
    public ResponseEntity<String> started() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setCacheControl(NO_CACHE);
        return new ResponseEntity<String>("Rest services are started.", httpHeaders, OK);
    }

    /**
     * Helps to check whether the system is alive. Body contains current system
     * time in milliseconds.
     */
    @RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
    public ResponseEntity<Long> heartbeat() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setCacheControl(NO_CACHE);
        return new ResponseEntity<>(System.currentTimeMillis(), httpHeaders, OK);
    }
}
