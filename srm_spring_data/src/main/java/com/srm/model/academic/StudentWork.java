package com.srm.model.academic;

import com.srm.model.BaseEntity;
import com.srm.model.people.Teacher;
import lombok.Builder;
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
@Entity
//POJO for HW, coursework, exams, quizzes, projects etc...
public class StudentWork extends BaseEntity {

    @Builder
    public StudentWork(Long id, String title, Integer maxScore, boolean contributor, Teacher teacherUploader,
                       Subject subject, AssignmentType assignmentType, Set<StudentResult> studentResults) {
        super(id);
        this.title = title;
        this.maxScore = maxScore;
        this.contributor = contributor;
        this.teacherUploader = teacherUploader;
        this.subject = subject;
        this.assignmentType = assignmentType;
        this.studentResults = studentResults;
    }

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
