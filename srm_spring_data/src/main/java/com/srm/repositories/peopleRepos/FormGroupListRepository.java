package com.srm.repositories.peopleRepos;

import com.srm.model.people.FormGroupList;
import com.srm.model.people.Teacher;
import org.springframework.data.repository.CrudRepository;

//the implementation of the following methods is supplied automatically by JPA

//declares additional SpringDataJPA CRUD functionality for FormGroupList
public interface FormGroupListRepository extends CrudRepository<FormGroupList, Long> {

    FormGroupList findByGroupName(String groupName);

    FormGroupList findByTeacher(Teacher teacher);
}
