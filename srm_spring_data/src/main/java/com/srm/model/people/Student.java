package com.srm.model.people;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "students")
public class Student extends Person {

    @JoinTable(name = "student_guardian",
            joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "guardian_id"))
    @ManyToMany
    private Set<Guardian> guardians = new HashSet<>();

    //no need for cascading
    @OneToOne
    private Teacher personalTutor;
}
