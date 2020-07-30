package com.srm.services.peopleServices;

import com.srm.model.people.Guardian;
import com.srm.model.people.Student;
import com.srm.services.CrudService;

import java.util.List;

public interface GuardianService extends CrudService<Guardian, Long> {

    //other methods not declared in CrudService

    Guardian findByLastName(String lastName);

    Guardian findByFirstAndLastName(String firstName, String lastName);

    List<Guardian> findAllByLastNameLike(String lastName);

    List<Guardian> findAllByFirstNameLikeAndLastNameLike(String firstName, String lastName);
}
