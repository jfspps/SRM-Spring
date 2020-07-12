package com.srm.model.services;

import com.srm.model.people.Guardian;

public interface GuardianService extends BaseService<Guardian, Long> {

    //other methods not declared in BaseService

    Guardian findByLastName(String lastName);
}
