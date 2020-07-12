package com.srm.repositories;

import com.srm.model.people.Guardian;
import org.springframework.data.repository.CrudRepository;

//provide CRUD functionality to Guardians
public interface GuardianRepository extends CrudRepository<Guardian, Long> {

    //implementation of findByLastName is supplied automatically by JPA
    Guardian findByLastName(String lastName);
}
