package com.brashevets.carshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brashevets.carshop.model.Car;

/**
 * Spring Data JPA repository for the Car entity.
 */
@Repository
public interface CarRepository extends JpaRepository<Car,Long> {

}
