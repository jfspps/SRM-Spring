package com.srm.services;

import com.srm.model.people.Guardian;

public interface GuardianService extends CrudService<Guardian, Long> {

    //other methods not declared in BaseService

    Guardian findByLastName(String lastName);
}
