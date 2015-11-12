package com.brashevets.carshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brashevets.carshop.model.car.BodyType;

/**
 * Spring Data JPA repository for the CarBodyType entity.
 */
@Repository
public interface CarBodyTypeRepository extends JpaRepository<BodyType,Long> {

}
