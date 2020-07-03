package com.srm.model.services;

import com.srm.model.people.Teacher;

import java.util.Set;

public interface TeacherService {

    //start of a few CRUD ops; these may be eventually declared in a CrudService generic interface and then extended here

    Teacher findById(Long id);

    Teacher findByName(String name);

    Teacher save(Teacher teacher);

    Set<Teacher> findAll();
}
