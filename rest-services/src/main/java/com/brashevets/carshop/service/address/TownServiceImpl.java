package com.brashevets.carshop.service.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brashevets.carshop.model.address.Town;
import com.brashevets.carshop.repository.TownRepository;

@Transactional(readOnly = true)
@Service
public class TownServiceImpl implements TownService {
    @Autowired
    private TownRepository townRepository;
    
    @Override
    public Town create(Long countryId, String name) {
        return townRepository.save(new Town(name, countryId));
    }

    @Override
    public Town create(Town town) {
        return townRepository.save(town);
    }

    @Override
    public Town update(Town town) {
        return townRepository.save(town);
    }

    @Override
    public void delete(Long id) {
        townRepository.delete(id);
    }

    @Override
    public Town get(Long id) {
       return townRepository.getOne(id);
    }

    @Override
    public List<Town> getAll() {
        return townRepository.findAll();
    }

}
