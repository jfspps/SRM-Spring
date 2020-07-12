package com.srm.services;

import com.srm.model.academic.Subject;

public interface SubjectService extends CrudService<Subject, Long> {

    Subject findBySubjectName(String subjectName);
}
