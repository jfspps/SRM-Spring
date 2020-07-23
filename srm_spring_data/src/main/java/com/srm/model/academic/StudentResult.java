package com.srm.model.academic;

import com.srm.model.BaseEntity;
import com.srm.model.people.Student;
import com.srm.model.people.Teacher;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class StudentResult extends BaseEntity {

    @Builder
    public StudentResult(Long id, Student student, Teacher teacherMarker, StudentWork studentWork, String score, String comments) {
        super(id);
        this.student = student;
        this.teacherMarker = teacherMarker;
        this.studentWork = studentWork;
        this.score = score;
        this.comments = comments;
    }

    @OneToOne
    private Student student;

    @OneToOne
    private Teacher teacherMarker;        //allow for different teachers to share the same assignment

    @ManyToOne
    private StudentWork studentWork;

    private String score;           //allow for numerical or letter based results
    private String comments;
}
