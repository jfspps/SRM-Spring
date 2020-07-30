package com.srm.repositories.peopleRepos;

import com.srm.model.people.Guardian;
import com.srm.model.people.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//the implementation of the following methods is supplied automatically by JPA

//declares additional SpringDataJPA CRUD functionality for Guardians
public interface GuardianRepository extends CrudRepository<Guardian, Long> {

    Guardian findByLastName(String lastName);

    Guardian findByFirstNameAndLastName(String firstName, String lastName);

    List<Guardian> findAllByLastNameLike(String lastName);

    List<Guardian> findAllByFirstNameLikeAndLastNameLike(String firstName, String lastName);
}
