package com.srm.repositories.peopleRepos;

import com.srm.model.people.Guardian;
import org.springframework.data.repository.CrudRepository;

//the implementation of the following methods is supplied automatically by JPA

//declares additional SpringDataJPA CRUD functionality for Guardians
public interface GuardianRepository extends CrudRepository<Guardian, Long> {

    Guardian findByLastName(String lastName);

    Guardian findByFirstNameAndLastName(String firstName, String lastName);
}
