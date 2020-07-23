package com.srm.services.peopleServices;

import com.srm.model.people.ContactDetail;
import com.srm.services.CrudService;

public interface ContactDetailService extends CrudService<ContactDetail, Long> {

    //other methods not declared in CrudService

    ContactDetail findByEmail(String email);

    ContactDetail findByPhone(String phoneNumber);
}
