package com.srm.services.academicServices;

import com.srm.model.academic.AssignmentType;
import com.srm.model.academic.StudentWork;
import com.srm.model.academic.Subject;
import com.srm.model.people.Teacher;
import com.srm.services.CrudService;

public interface StudentWorkService extends CrudService<StudentWork, Long> {

    //other methods not declared in CrudService

    StudentWork findByTitle(String title);

    StudentWork findByTeacher(Teacher uploader);

    StudentWork findBySubject(Subject subject);

    StudentWork findByAssignmentType(AssignmentType assignmentType);

    StudentWork findByContribution(boolean isAContributor);
}
