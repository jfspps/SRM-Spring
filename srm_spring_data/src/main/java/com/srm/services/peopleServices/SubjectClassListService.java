package com.srm.services.peopleServices;

import com.srm.model.people.SubjectClassList;
import com.srm.services.CrudService;

public interface SubjectClassListService extends CrudService<SubjectClassList, Long> {

    //other methods not declared in CrudService

    SubjectClassList findBySubject(String subjectName);

    SubjectClassList findByTeacherLastName(String lastName);

    SubjectClassList findByGroupName(String groupName);
}
