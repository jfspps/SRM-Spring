package com.srm.model.people;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "guardians")
public class Guardian extends Person {

    @Builder
    public Guardian(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @ManyToOne
    private Address address;

    //"guardians" refers to the Set<Guardian> fro Student
    @ManyToMany(mappedBy = "guardians")
    private Set<Student> students = new HashSet<>();
}