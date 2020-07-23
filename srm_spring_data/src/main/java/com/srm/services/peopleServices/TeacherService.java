package com.srm.services.peopleServices;

import com.srm.model.people.Teacher;
import com.srm.services.CrudService;

public interface TeacherService extends CrudService<Teacher, Long> {

    //other methods not declared in CrudService

    Teacher findByLastName(String lastName);
}