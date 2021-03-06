package com.srm.services.peopleServices;

import com.srm.model.people.FormGroupList;
import com.srm.services.CrudService;

public interface FormGroupListService extends CrudService<FormGroupList, Long> {

    //other methods not declared in CrudService

    FormGroupList findByGroupName(String groupName);

    FormGroupList findByTeacherLastName(String lastName);

    FormGroupList findByTeacherFirstAndLastName(String firstName, String lastName);
}
