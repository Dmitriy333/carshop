package com.brashevets.carshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brashevets.carshop.model.car.DefaultCarMeta;

/**
 * Spring Data JPA repository for the DefaultCarMeta entity.
 */
@Repository
public interface DefaultCarMetaRepository extends JpaRepository<DefaultCarMeta,Long> {

}
