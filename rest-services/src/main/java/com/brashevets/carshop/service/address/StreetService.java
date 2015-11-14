package com.brashevets.carshop.service.address;

import java.util.List;

import com.brashevets.carshop.model.address.Street;

public interface StreetService {
    Street create(Long countryId, String name);

    Street create(Street street);

    Street update(Street street);

    void delete(Long id);

    Street get(Long id);

    List<Street> getAll();
}
