package com.srm.model.academic;

import com.srm.model.BaseEntity;
import com.srm.model.people.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
//POJO for HW, coursework, exams, quizzes, projects etc...
public class StudentWork extends BaseEntity {

    private String title;
    private Integer maxScore;               //boxed Integer can be null; allows for letter grades or no score at all
    private boolean contributor = true;     //does this contribute to an overall end-of-term/semester/year score?

    @OneToOne
    private Teacher teacherUploader;

    @OneToOne
    private Subject subject;

    //allow for extension, offloading responsibility from StudentWork
    @ManyToOne
    private AssignmentType assignmentType;

    @OneToMany(mappedBy = "studentWork")
    private Set<StudentResult> studentResults = new HashSet<>();
}
