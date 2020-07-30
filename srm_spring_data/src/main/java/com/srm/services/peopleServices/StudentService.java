package com.srm.services.peopleServices;

import com.srm.model.people.Student;
import com.srm.services.CrudService;

import java.util.List;

public interface StudentService extends CrudService<Student, Long> {

    //other methods not declared in CrudService

    Student findByLastName(String lastName);

    Student findByFirstAndLastName(String firstName, String lastName);

    List<Student> findAllByLastNameLike(String lastName);
}
