package com.srm.model.people;

import com.srm.model.academic.Subject;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "teachers")
public class Teacher extends Person {

    @Builder
    public Teacher(Long id, String firstName, String lastName, Set<Subject> subjects, ContactDetail contactDetail,
                   String department) {
        super(id, firstName, lastName, contactDetail);
        if(subjects != null){
            this.subjects = subjects;
            this.department = department;
        }
    }

    @JoinTable(name = "teacher_subject",
            joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    @ManyToMany
    private Set<Subject> subjects = new HashSet<>();

    private String department;
}
