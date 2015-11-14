package com.brashevets.carshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brashevets.carshop.model.car.Model;

/**
 * Spring Data JPA repository for the CarModel entity.
 */
@Repository
public interface CarModelRepository extends JpaRepository<Model,Long> {

}
