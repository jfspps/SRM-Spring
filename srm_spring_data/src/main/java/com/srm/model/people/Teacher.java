package com.srm.model.people;

import com.srm.model.academic.Subject;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teachers")
public class Teacher extends Person {

    @Builder
    public Teacher(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @JoinTable(name = "teacher_subject",
            joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    @ManyToMany
    private Set<Subject> subjects = new HashSet<>();

}
