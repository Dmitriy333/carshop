package com.brashevets.carshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brashevets.carshop.model.Dealer;

/**
 * Spring Data JPA repository for the Dealer entity.
 */
@Repository
public interface DealerRepository extends JpaRepository<Dealer,Long> {

}
