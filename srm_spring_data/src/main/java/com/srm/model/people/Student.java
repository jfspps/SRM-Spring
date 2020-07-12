package com.srm.model.people;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends Person {

    @JoinTable(name = "student_guardian",
            joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "guardian_id"))
    @ManyToMany
    private Set<Guardian> guardian = new HashSet<>();

    //no need for cascading
    @OneToOne
    private Teacher personalTutor;

    public Teacher getPersonalTutor() {
        return personalTutor;
    }

    public void setPersonalTutor(Teacher personalTutor) {
        this.personalTutor = personalTutor;
    }

    public Set<Guardian> getGuardian() {
        return guardian;
    }

    public void setGuardian(Set<Guardian> guardian) {
        this.guardian = guardian;
    }
}
