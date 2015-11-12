package com.brashevets.carshop.service.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brashevets.carshop.model.address.Street;
import com.brashevets.carshop.repository.StreetRepository;

@Transactional(readOnly = true)
@Service
public class StreetServiceImpl implements StreetService {
    @Autowired
    private StreetRepository streetRepository;

    @Transactional
    @Override
    public Street create(Long townId, String name) {
        return streetRepository.save(new Street(name, townId));
    }

    @Transactional
    @Override
    public Street create(Street street) {
        return streetRepository.save(street);
    }

    @Transactional
    @Override
    public Street update(Street street) {
        return streetRepository.save(street);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        streetRepository.delete(id);
    }

    @Override
    public Street get(Long id) {
        return streetRepository.findOne(id);
    }

    @Override
    public List<Street> getAll() {
        return streetRepository.findAll();
    }

}
