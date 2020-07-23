package com.srm.services.academicServices;

import com.srm.model.academic.Subject;
import com.srm.services.CrudService;

public interface SubjectService extends CrudService<Subject, Long> {

    //other methods not declared in CrudService

    Subject findBySubjectName(String subjectName);
}
