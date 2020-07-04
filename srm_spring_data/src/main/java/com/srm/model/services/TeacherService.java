package com.srm.model.services;

import com.srm.model.people.Teacher;

public interface TeacherService extends BaseService<Teacher, Long> {

    //other methods not declared in BaseService

    Teacher findByName(String name);
}