package com.brashevets.carshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brashevets.carshop.model.address.Country;

/**
 * Spring Data JPA repository for the Country entity.
 */
@Repository
public interface CountryRepository extends JpaRepository<Country,Long> {

}
