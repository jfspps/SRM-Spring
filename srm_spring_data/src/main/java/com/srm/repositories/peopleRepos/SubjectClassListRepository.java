package com.srm.repositories.peopleRepos;

import com.srm.model.people.SubjectClassList;
import org.springframework.data.repository.CrudRepository;

//the implementation of the following methods is supplied automatically by JPA

//declares additional SpringDataJPA CRUD functionality for SubjectClassList
public interface SubjectClassListRepository extends CrudRepository<SubjectClassList, Long> {

    SubjectClassList findBySubject_SubjectName(String subject);

    SubjectClassList findByTeacher_LastName(String teacherLastName);

    SubjectClassList findByGroupName(String groupName);
}
