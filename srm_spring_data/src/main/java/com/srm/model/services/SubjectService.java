package com.srm.model.services;

import com.srm.model.academic.Subject;

public interface SubjectService extends BaseService<Subject, Long> {

    Subject findBySubjectName(String subjectName);
}
