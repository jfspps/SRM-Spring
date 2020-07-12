package com.srm.repositories;

import com.srm.model.people.Teacher;
import org.springframework.data.repository.CrudRepository;

//provide CRUD functionality to Teachers
public interface TeacherRepository extends CrudRepository<Teacher, Long> {

    //implementation of findByLastName is supplied automatically by JPA
    Teacher findByLastName(String lastName);
}
