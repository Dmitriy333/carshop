package com.brashevets.carshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brashevets.carshop.model.address.Address;

/**
 * Spring Data JPA repository for the Address entity.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

}
