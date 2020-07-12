package com.srm.model.people;

import com.srm.model.academic.Subject;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher extends Person {

    @JoinTable(name = "teacher_subject",
            joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    @ManyToMany
    private Set<Subject> subject;

    public Set<Subject> getSubject() {
        return subject;
    }

    public void setSubject(Set<Subject> subject) {
        this.subject = subject;
    }
}
