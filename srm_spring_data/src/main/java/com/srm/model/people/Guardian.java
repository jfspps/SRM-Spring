package com.srm.model.people;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Setter
@Getter
@Entity
@Table(name = "guardians")
public class Guardian extends Person {

    // future: if there is a need to query which students a guardian is responsible for then this POJO may need
    // Set<Student> with ManyToMany relationship

    @OneToOne
    private Address address;

}