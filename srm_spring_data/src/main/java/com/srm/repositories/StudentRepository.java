package com.srm.repositories;

import com.srm.model.people.Student;
import org.springframework.data.repository.CrudRepository;

//declares SpringDataJPA CRUD functionality for Students
public interface StudentRepository extends CrudRepository<Student, Long> {

    //implementation of findByLastName is supplied automatically by JPA
    Student findByLastName(String lastName);
}
