package com.srm.repositories.peopleRepos;

import com.srm.model.people.Student;
import org.springframework.data.repository.CrudRepository;

//the implementation of the following methods is supplied automatically by JPA

//declares additional SpringDataJPA CRUD functionality for Students
public interface StudentRepository extends CrudRepository<Student, Long> {

    Student findByLastName(String lastName);
}
