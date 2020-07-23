package com.srm.services.academicServices;

import com.srm.model.academic.StudentResult;
import com.srm.services.CrudService;

public interface StudentResultService extends CrudService<StudentResult, Long> {

    //other methods not declared in CrudService

    StudentResult findByStudentLastName(String lastName);

    StudentResult findByTeacherLastName(String lastName);

    StudentResult findByStudentWorkTitle(String assignmentTitle);

    StudentResult findByScore(String score);

    //numerous other possible search parameters possible
}
