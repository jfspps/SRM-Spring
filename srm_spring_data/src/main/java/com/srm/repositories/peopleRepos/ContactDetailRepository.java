package com.srm.repositories.peopleRepos;

import com.srm.model.people.ContactDetail;
import org.springframework.data.repository.CrudRepository;

//the implementation of the following methods is supplied automatically by JPA

//declares additional SpringDataJPA CRUD functionality for ContactDetail
public interface ContactDetailRepository extends CrudRepository<ContactDetail, Long> {

    ContactDetail findByEmail(String email);

    ContactDetail findByPhoneNumber(String phoneNumber);
}
