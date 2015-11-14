package com.brashevets.carshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brashevets.carshop.model.address.Street;

/**
 * Spring Data JPA repository for the Street entity.
 */
@Repository
public interface StreetRepository extends JpaRepository<Street,Long> {

}
