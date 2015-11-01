package com.brashevets.carshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brashevets.carshop.model.CarImage;

/**
 * Spring Data JPA repository for the CarImage entity.
 */
@Repository
public interface CarImageRepository extends JpaRepository<CarImage,Long> {

}
