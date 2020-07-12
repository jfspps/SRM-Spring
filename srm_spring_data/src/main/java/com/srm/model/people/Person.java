package com.srm.model.people;

import com.srm.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@MappedSuperclass
public class Person extends BaseEntity {

    //DB id's are handled by BaseEntity

    //Hibernate uses snake case by default so the name argument is somewhat redundant here
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    //transferred to Student, Teacher and Guardian??? wait and see
    @OneToOne
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
