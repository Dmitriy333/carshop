package com.brashevets.carshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brashevets.carshop.model.CarMake;

/**
 * Spring Data JPA repository for the CarMake entity.
 */
@Repository
public interface CarMakeRepository extends JpaRepository<CarMake,Long> {

}
