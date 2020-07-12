package com.srm.model.services;

import com.srm.model.people.Student;

public interface StudentService extends BaseService<Student, Long> {

    //other methods not declared in BaseService

    Student findByLastName(String lastName);
}
