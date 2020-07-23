package com.srm.repositories.peopleRepos;

import com.srm.model.academic.Subject;
import com.srm.model.people.SubjectClassList;
import com.srm.model.people.Teacher;
import org.springframework.data.repository.CrudRepository;

//the implementation of the following methods is supplied automatically by JPA

//declares additional SpringDataJPA CRUD functionality for SubjectClassList
public interface SubjectClassListRepository extends CrudRepository<SubjectClassList, Long> {

    SubjectClassList findBySubject(Subject subject);

    SubjectClassList findByTeacher(Teacher teacher);

    SubjectClassList findByGroupName(String groupName);
}
