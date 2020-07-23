package com.srm.repositories.academicRepos;

import com.srm.model.academic.ThresholdList;
import org.springframework.data.repository.CrudRepository;

//the implementation of the following methods is supplied automatically by JPA

//declares additional SpringDataJPA CRUD functionality for ThresholdList
public interface ThresholdListRepository extends CrudRepository<ThresholdList, Long> {

}
