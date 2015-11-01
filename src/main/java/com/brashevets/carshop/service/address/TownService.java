package com.brashevets.carshop.service.address;

import java.util.List;

import com.brashevets.carshop.model.Town;

public interface TownService {

    Town create(Long countryId, String name);

    Town create(Town address);

    Town update(Town address);

    void delete(Long id);

    Town get(Long id);

    List<Town> getAll();
}
