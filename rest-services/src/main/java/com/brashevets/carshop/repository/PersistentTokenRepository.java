package com.brashevets.carshop.repository;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brashevets.carshop.model.token.PersistentToken;
import com.brashevets.carshop.model.user.User;

/**
 * Spring Data JPA repository for the PersistentToken entity.
 */
//@Repository
public interface PersistentTokenRepository {//extends JpaRepository<PersistentToken, String> {

    List<PersistentToken> findByUser(User user);

    List<PersistentToken> findByTokenDateBefore(LocalDate localDate);

}
