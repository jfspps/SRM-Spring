package com.srm.model.people;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "guardians")
public class Guardian extends Person {

    @OneToOne
    private Address address;

    //"guardians" refers to the Set<Guardian> fro Student
    @ManyToMany(mappedBy = "guardians")
    private Set<Student> students = new HashSet<>();
}