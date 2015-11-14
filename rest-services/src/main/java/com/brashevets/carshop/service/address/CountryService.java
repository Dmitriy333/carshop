package com.brashevets.carshop.service.address;

import java.util.List;

import com.brashevets.carshop.model.address.Country;

public interface CountryService {
    Country create(String name);

    Country create(Country country);

    Country update(Country country);

    void delete(Long id);

    Country get(Long id);

    List<Country> getAll();
}
