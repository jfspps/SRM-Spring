package com.srm.repositories;

import com.srm.model.academic.Subject;
import org.springframework.data.repository.CrudRepository;

//declares SpringDataJPA CRUD functionality for Subjects
public interface SubjectRepository extends CrudRepository<Subject, Long> {

    Subject findBySubjectName(String subjectName);
}
