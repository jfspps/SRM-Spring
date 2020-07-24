package com.srm.services.academicServices;

import com.srm.model.academic.AssignmentType;
import com.srm.services.CrudService;

public interface AssignmentTypeService extends CrudService<AssignmentType, Long> {

    //other methods not declared in CrudService

    AssignmentType findByDescription(String type);
}
