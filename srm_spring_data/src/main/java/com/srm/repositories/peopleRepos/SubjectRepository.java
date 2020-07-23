package com.srm.repositories.peopleRepos;

import com.srm.model.academic.Subject;
import org.springframework.data.repository.CrudRepository;

//the implementation of the following methods is supplied automatically by JPA

//declares additional SpringDataJPA CRUD functionality for Subjects
public interface SubjectRepository extends CrudRepository<Subject, Long> {

    Subject findBySubjectName(String subjectName);
}
