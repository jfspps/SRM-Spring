package com.srm.model.people;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
                   Set<SubjectClassList> subjectClassLists, FormGroupList formGroupList, ContactDetail contactDetail) {
        super(id, firstName, lastName, contactDetail);

        if(guardians != null){
            this.guardians = guardians;
        }
        this.teacher = personalTutor;

        if(subjectClassLists != null){
            this.subjectClassLists = subjectClassLists;
        }
        this.formGroupList = formGroupList;
    }

    //the @JsonIgnore added to prevent Spring from creating infinitely long JSONs
    //(https://stackoverflow.com/questions/20813496/tomcat-exception-cannot-call-senderror-after-the-response-has-been-committed)
    @JsonIgnore
    @JoinTable(name = "student_guardian",
            joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "guardian_id"))
    @ManyToMany
    private Set<Guardian> guardians = new HashSet<>();

    //personal tutor; no need for cascading
    @OneToOne
    private Teacher teacher;


    @JsonIgnore
    @JoinTable(name = "student_subjectlist",
            joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "subjectclasslist_id"))
    @ManyToMany
    private Set<SubjectClassList> subjectClassLists = new HashSet<>();


    @JsonIgnore
    @ManyToOne
    private FormGroupList formGroupList;
}
