package com.srm.model.people;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "guardians")
public class Guardian extends Person {

    @Builder
    public Guardian(Long id, String firstName, String lastName, Address address, Set<Student> students) {
        super(id, firstName, lastName);
        this.address = address;
        this.students = students;
    }

    @ManyToOne
    private Address address;

    //"guardians" refers to the Set<Guardian> fro Student
    @ManyToMany(mappedBy = "guardians")
    private Set<Student> students = new HashSet<>();
}