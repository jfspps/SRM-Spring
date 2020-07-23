package com.srm.repositories.peopleRepos;

import com.srm.model.people.Address;
import org.springframework.data.repository.CrudRepository;

//the implementation of the following methods is supplied automatically by JPA ***

//declares additional SpringDataJPA CRUD functionality for Address
public interface AddressRepository extends CrudRepository<Address, Long> {

    //*** important to allow IDE to help select identifier of postcode (can't always reference otherwise) ??
    Address findByPostcode(String postcode);
}
