package com.srm.services.peopleServices;

import com.srm.model.people.Address;
import com.srm.services.CrudService;

public interface AddressService extends CrudService<Address, Long> {

    //other methods not declared in CrudService

    Address findByPostCode(String postcode);
}
