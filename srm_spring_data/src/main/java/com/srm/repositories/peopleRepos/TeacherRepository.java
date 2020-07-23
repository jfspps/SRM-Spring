package com.srm.repositories.peopleRepos;

import com.srm.model.people.Teacher;
import org.springframework.data.repository.CrudRepository;

//the implementation of the following methods is supplied automatically by JPA

//declares additional SpringDataJPA CRUD functionality for Teachers
public interface TeacherRepository extends CrudRepository<Teacher, Long> {

    Teacher findByLastName(String lastName);
}
