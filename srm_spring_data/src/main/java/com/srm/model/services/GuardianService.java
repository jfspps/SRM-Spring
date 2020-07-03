package com.srm.model.services;

import com.srm.model.people.Guardian;

import java.util.Set;

public interface GuardianService {

    //start of a few CRUD ops; these may be eventually declared in a CrudService generic interface and then extended here

    Guardian findById(Long id);

    Guardian findByName(String name);

    Guardian save(Guardian guardian);

    Set<Guardian> findAll();
}
