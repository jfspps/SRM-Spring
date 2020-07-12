package com.srm.model.services;

import com.srm.model.people.Teacher;

public interface TeacherService extends CrudService<Teacher, Long> {

    //other methods not declared in BaseService

    Teacher findByLastName(String lastName);
}