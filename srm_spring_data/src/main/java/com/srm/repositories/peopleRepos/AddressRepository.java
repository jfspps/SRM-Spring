package com.srm.repositories.peopleRepos;

import com.srm.model.people.Address;
import org.springframework.data.repository.CrudRepository;

//the implementation of the following methods is supplied automatically by JPA ***

//declares additional SpringDataJPA CRUD functionality for Address
public interface AddressRepository extends CrudRepository<Address, Long> {

    //*** important that the case of 'postcode' in 'findBypostcode' matches that in Address
    Address findBypostcode(String postcode);
}
