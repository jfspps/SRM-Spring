package com.srm.repositories.academicRepos;

import com.srm.model.academic.AssignmentType;
import org.springframework.data.repository.CrudRepository;

//the implementation of the following methods is supplied automatically by JPA

//declares additional SpringDataJPA CRUD functionality for AssignmentType
public interface AssignmentTypeRepository extends CrudRepository<AssignmentType, Long> {

    AssignmentType findByDescription(String description);
}
