package com.srm.services;

import com.srm.model.people.Teacher;

public interface TeacherService extends CrudService<Teacher, Long> {

    //other methods not declared in CrudService

    Teacher findByLastName(String lastName);
}