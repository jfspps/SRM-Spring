package com.srm.services.academicServices;

import com.srm.model.academic.StudentWork;
import com.srm.services.CrudService;

public interface StudentWorkService extends CrudService<StudentWork, Long> {

    //other methods not declared in CrudService

    StudentWork findByTitle(String title);

    StudentWork findByTeacherLastName(String lastName);

    StudentWork findBySubject(String subjectName);

    StudentWork findByAssignmentType(String assignmentType);

    StudentWork findByContribution(boolean isAContributor);
}
