package com.srm.repositories.academicRepos;

import com.srm.model.academic.Threshold;
import org.springframework.data.repository.CrudRepository;


//the implementation of the following methods is supplied automatically by JPA

//declares additional SpringDataJPA CRUD functionality for Threshold
public interface ThresholdRepository extends CrudRepository<Threshold, Long> {

    Threshold findByNumerical(int numericalBoundary);

    Threshold findByAlphabetical(String letterGrade);
}
