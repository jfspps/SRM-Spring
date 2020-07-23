package com.srm.services;

import com.srm.model.academic.Subject;

public interface SubjectService extends CrudService<Subject, Long> {

    //other methods not declared in CrudService

    Subject findBySubjectName(String subjectName);
}
