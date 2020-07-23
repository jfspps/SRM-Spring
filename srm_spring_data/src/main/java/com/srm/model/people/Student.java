package com.srm.model.people;

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
@Table(name = "students")
public class Student extends Person {

    @Builder
    public Student(Long id, String firstName, String lastName, Set<Guardian> guardians, Teacher personalTutor,
                   Set<SubjectClassList> subjectClassLists, FormGroupList formGroupList) {
        super(id, firstName, lastName);
        this.guardians = guardians;
        this.teacher = personalTutor;
        this.subjectClassLists = subjectClassLists;
        this.formGroupList = formGroupList;
    }

    @JoinTable(name = "student_guardian",
            joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "guardian_id"))
    @ManyToMany
    private Set<Guardian> guardians = new HashSet<>();

    //personal tutor; no need for cascading
    @OneToOne
    private Teacher teacher;

    @JoinTable(name = "student_subjectlist",
            joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "subjectclasslist_id"))
    @ManyToMany
    private Set<SubjectClassList> subjectClassLists = new HashSet<>();

    @ManyToOne
    private FormGroupList formGroupList;
}
