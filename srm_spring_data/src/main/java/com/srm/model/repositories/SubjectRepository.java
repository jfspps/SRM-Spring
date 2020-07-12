package com.srm.model.repositories;

import com.srm.model.academic.Subject;
import org.springframework.data.repository.CrudRepository;

//provide CRUD functionality to Subjects
public interface SubjectRepository extends CrudRepository<Subject, Long> {
}
