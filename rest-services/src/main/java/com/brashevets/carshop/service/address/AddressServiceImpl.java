package com.brashevets.carshop.service.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brashevets.carshop.model.address.Address;
import com.brashevets.carshop.repository.AddressRepository;
import com.brashevets.carshop.repository.CountryRepository;
import com.brashevets.carshop.repository.StreetRepository;
import com.brashevets.carshop.repository.TownRepository;

@Transactional(readOnly = true)
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private TownRepository townRepository;
    @Autowired
    private StreetRepository streetRepository;
    @Autowired
    private CountryRepository countryRepository;

    @Transactional
    @Override
    public Address create(Long streetId, Long buildingNumber, Long flatNumber) {
        Address address = new Address();
        address.setBuildingNumber(buildingNumber);
        address.setFlatNumber(flatNumber);
        address.setStreetId(streetId);
        return addressRepository.save(address);
    }

    @Transactional
    @Override
    public Address update(Address address) {
        return addressRepository.save(address);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        addressRepository.delete(id);
    }

    @Transactional
    @Override
    public Address create(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address get(Long id) {
        return addressRepository.getOne(id);
    }

    @Override
    public List<Address> getAll() {
        return addressRepository.findAll();
    }

}
