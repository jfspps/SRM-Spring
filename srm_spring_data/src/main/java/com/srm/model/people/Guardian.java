package com.srm.model.people;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "guardians")
public class Guardian extends Person {

    //future: if there is a need to query which students a guardian is responsible for then this POJO may need Set<Student>

    @OneToOne
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}