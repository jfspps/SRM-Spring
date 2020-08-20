package com.srm.services.map;

import com.srm.model.people.Address;
import com.srm.services.peopleServices.AddressService;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@NoArgsConstructor
@Profile("map")
public class AddressMapService extends AbstractMapService<Address, Long> implements AddressService {

    @Override
    public Address save(Address address) {
        if (address != null) {
            return super.save(address);
        } else {
            System.out.println("Empty object passed to Address()");
            return null;
        }
    }

    @Override
    public Address findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Set<Address> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(Address objectT) {
        super.delete(objectT);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public Address findByPostCode(String postcode) {
        return this.findAll()
                .stream()
                .filter(address -> address.getPostcode().equalsIgnoreCase(postcode))
                .findFirst()
                .orElse(null);
    }
}
