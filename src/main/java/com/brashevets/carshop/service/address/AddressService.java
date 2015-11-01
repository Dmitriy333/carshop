package com.brashevets.carshop.service.address;

import java.util.List;

import com.brashevets.carshop.model.Address;

public interface AddressService {
    Address create(Long streetId, Long buildingNumber, Long flatNumber);

    Address create(Address address);

    Address update(Address address);

    void delete(Long id);

    Address get(Long id);

    List<Address> getAll();
}
