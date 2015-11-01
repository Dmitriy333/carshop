package com.brashevets.carshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brashevets.carshop.model.Town;

/**
 * Spring Data JPA repository for the Town entity.
 */
@Repository
public interface TownRepository extends JpaRepository<Town,Long> {

}
