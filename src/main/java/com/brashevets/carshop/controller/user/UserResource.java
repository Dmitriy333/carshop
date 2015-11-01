package com.brashevets.carshop.controller.user;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.brashevets.carshop.controller.PathMapping;
import com.brashevets.carshop.controller.dto.ManagedUserDTO;
import com.brashevets.carshop.controller.util.HeaderUtil;
import com.brashevets.carshop.model.Authority;
import com.brashevets.carshop.model.User;
import com.brashevets.carshop.repository.AuthorityRepository;
import com.brashevets.carshop.repository.UserRepository;
import com.brashevets.carshop.security.AuthoritiesConstants;
import com.brashevets.carshop.service.UserService;

/**
 * REST controller for managing users.
 *
 * <p>
 * This class accesses the User entity, and needs to fetch its collection of
 * authorities.
 * </p>
 * <p>
 * For a normal use-case, it would be better to have an eager relationship
 * between User and Authority, and send everything to the client side: there
 * would be no DTO, a lot less code, and an outer-join which would be good for
 * performance.
 * </p>
 * <p>
 * We use a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities,
 * because people will quite often do relationships with the user, and we don't
 * want them to get the authorities all the time for nothing (for performance
 * reasons). This is the #1 goal: we should not impact our users' application
 * because of this use-case.</li>
 * <li>Not having an outer join causes n+1 requests to the database. This is not
 * a real issue as we have by default a second-level cache. This means on the
 * first HTTP call we do the n+1 requests, but then all authorities come from
 * the cache, so in fact it's much better than doing an outer join (which will
 * get lots of data from the database, for each HTTP call).</li>
 * <li>As this manages users, for security reasons, we'd rather have a DTO
 * layer.</li>
 * </p>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this
 * case.
 * </p>
 */
@RestController
@RequestMapping("/users")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    private UserService userService;

    /**
     * POST /users -> Create a new user.
     */
    @RequestMapping(value = PathMapping.DEFAULT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<User> createUser(@RequestBody User user) throws URISyntaxException {
        log.debug("REST request to save User : {}", user);
        if (user.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new user cannot already have an ID").body(null);
        }
        User result = userRepository.save(user);
        return ResponseEntity.created(new URI("/api/users/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("user", result.getId().toString())).body(result);
    }

    /**
     * PUT /users -> Updates an existing User.
     */
    @RequestMapping(value = PathMapping.DEFAULT, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<ManagedUserDTO> updateUser(@RequestBody ManagedUserDTO managedUserDTO)
            throws URISyntaxException {
        log.debug("REST request to update User : {}", managedUserDTO);
        User user = userRepository.findOne(managedUserDTO.getId());
        if (user != null) {
            user.setId(managedUserDTO.getId());
            user.setFirstName(managedUserDTO.getFirstName());
            user.setLastName(managedUserDTO.getLastName());
            user.setEmail(managedUserDTO.getEmail());
            user.setActivated(managedUserDTO.isActivated());
            user.setLangKey(managedUserDTO.getLangKey());
            Set<Authority> authorities = user.getAuthorities();
            authorities.clear();
            for (String authority : managedUserDTO.getAuthorities()) {
                authorities.add(authorityRepository.findOne(authority));
            }
            return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("user", managedUserDTO.getLogin()))
                    .body(new ManagedUserDTO(userRepository.findOne(managedUserDTO.getId())));
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET /users -> get all users.
     */
    @RequestMapping(value = PathMapping.DEFAULT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    public ResponseEntity<List<ManagedUserDTO>> getAllUsers() throws URISyntaxException {
        List<User> users = userRepository.findAll();
        List<ManagedUserDTO> managedUserDTOs = new ArrayList<>();
        for (User user : users) {
            ManagedUserDTO managedUserDTO = new ManagedUserDTO(user);
            managedUserDTOs.add(managedUserDTO);
        }
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(users, "/api/users");
        //return new ResponseEntity<>(managedUserDTOs, headers, HttpStatus.OK);
        return new ResponseEntity<>(managedUserDTOs, HttpStatus.OK);
    }

    /**
     * GET /users/:login -> get the "login" user.
     */
    @ResponseBody
    @RequestMapping(value = "/{login}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    public ResponseEntity<ManagedUserDTO> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        User user = userService.getUserWithAuthoritiesByLogin(login);
        System.out.println(user);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ManagedUserDTO(user), HttpStatus.OK);
    }
}
