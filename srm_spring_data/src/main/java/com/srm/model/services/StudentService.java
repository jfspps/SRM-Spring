package com.srm.model.services;

import com.srm.model.people.Student;

import java.util.Set;

public interface StudentService {

    //start of a few CRUD ops; these may be eventually declared in a CrudService generic interface and then extended here

    Student findById(Long id);

    Student findByName(String name);

    Student save(Student student);

    Set<Student> findAll();
}
