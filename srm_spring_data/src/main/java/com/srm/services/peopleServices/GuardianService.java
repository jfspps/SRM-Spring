package com.srm.services.peopleServices;

import com.srm.model.people.Guardian;
import com.srm.services.CrudService;

public interface GuardianService extends CrudService<Guardian, Long> {

    //other methods not declared in CrudService

    Guardian findByLastName(String lastName);
}
