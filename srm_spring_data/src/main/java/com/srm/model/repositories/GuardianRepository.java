package com.srm.model.repositories;

import com.srm.model.people.Guardian;
import org.springframework.data.repository.CrudRepository;

//provide CRUD functionality to Guardians
public interface GuardianRepository extends CrudRepository<Guardian, Long> {
}
