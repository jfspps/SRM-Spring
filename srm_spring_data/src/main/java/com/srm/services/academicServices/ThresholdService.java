package com.srm.services.academicServices;

import com.srm.model.academic.Threshold;
import com.srm.services.CrudService;

public interface ThresholdService extends CrudService<Threshold, Long> {

    //other methods not declared in CrudService

    Threshold findByNumericalBoundary(String numericalBoundary);

    Threshold findByLetterGrade(String letterGrade);
}
