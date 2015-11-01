package com.brashevets.carshop.service.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brashevets.carshop.model.Country;
import com.brashevets.carshop.repository.CountryRepository;

@Transactional(readOnly = true)
@Service
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryRepository countryRepository;

    @Transactional
    @Override
    public Country create(String name) {
        return countryRepository.save(new Country(name));
    }

    @Transactional
    @Override
    public Country create(Country country) {
        return countryRepository.save(country);
    }

    @Transactional
    @Override
    public Country update(Country country) {
        return countryRepository.save(country);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        countryRepository.delete(id);
    }

    @Override
    public Country get(Long id) {
        return countryRepository.findOne(id);
    }

    @Override
    public List<Country> getAll() {
        return countryRepository.findAll();
    }

}
