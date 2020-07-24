package com.srm.services.academicServices;

import com.srm.model.academic.StudentWork;
import com.srm.services.CrudService;

public interface StudentWorkService extends CrudService<StudentWork, Long> {

    //other methods not declared in CrudService

    StudentWork findByTitle(String title);

    StudentWork findByTeacherLastName(String lastName);

    StudentWork findByTeacherFirstAndLastName(String firstName, String lastName);

    StudentWork findBySubject(String subjectName);

    StudentWork findByDescription(String description);

    StudentWork findByContribution(boolean isAContributor);
}
