package com.srm.services.peopleServices;

import com.srm.model.people.Student;
import com.srm.model.people.Teacher;
import com.srm.services.CrudService;

import java.util.List;

public interface TeacherService extends CrudService<Teacher, Long> {

    //other methods not declared in CrudService

    Teacher findByLastName(String lastName);

    Teacher findByFirstAndLastName(String firstName, String lastName);

    List<Teacher> findAllByLastNameLike(String lastName);

    List<Teacher> findAllByFirstNameLikeAndLastNameLike(String firstName, String lastName);

    List<Teacher> findAllByDepartment(String department);

    //assume two teachers with the same name do not teach in the same department!
    Teacher findByFirstNameAndLastNameAndDepartment(String firstName, String lastName, String department);
}