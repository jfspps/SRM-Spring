package com.srm.model.people;

import com.srm.model.BaseEntity;

public class Person extends BaseEntity {

    //DB id's are handled by BaseEntity

    private String firstName;
    private String lastName;
    private ContactDetail contactDetail;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ContactDetail getContactDetail() {
        return contactDetail;
    }

    public void setContactDetail(ContactDetail contactDetail) {
        this.contactDetail = contactDetail;
    }
}
