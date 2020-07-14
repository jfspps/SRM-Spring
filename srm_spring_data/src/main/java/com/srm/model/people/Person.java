package com.srm.model.people;

import com.srm.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;


@Setter
@Getter
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

}
