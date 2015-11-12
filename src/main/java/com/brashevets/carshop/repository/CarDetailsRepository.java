package com.brashevets.carshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brashevets.carshop.model.car.CarDetails;

/**
 * Spring Data JPA repository for the CarDetails entity.
 */
@Repository
public interface CarDetailsRepository extends JpaRepository<CarDetails,Long> {

}
