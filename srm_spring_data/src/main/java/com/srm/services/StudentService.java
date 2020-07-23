package com.srm.services;

import com.srm.model.people.Student;

public interface StudentService extends CrudService<Student, Long> {

    //other methods not declared in CrudService

    Student findByLastName(String lastName);
}
