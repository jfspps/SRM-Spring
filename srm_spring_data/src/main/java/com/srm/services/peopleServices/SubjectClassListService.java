package com.srm.services.peopleServices;

import com.srm.model.academic.Subject;
import com.srm.model.people.SubjectClassList;
import com.srm.model.people.Teacher;
import com.srm.services.CrudService;

public interface SubjectClassListService extends CrudService<SubjectClassList, Long> {

    //other methods not declared in CrudService

    SubjectClassList findBySubject(Subject subject);

    SubjectClassList findByTeacher(Teacher teacher);

    SubjectClassList findByGroupName(String groupName);
}
