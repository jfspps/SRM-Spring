package com.srm.model.academic;

import com.srm.model.BaseEntity;
import com.srm.model.people.Student;
import com.srm.model.people.Teacher;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Report extends BaseEntity {

    @Builder
    public Report(Long id, Student student, Teacher teacher, Subject subject, String comments) {
        super(id);
        this.student = student;
        this.teacher = teacher;
        this.subject = subject;
        this.comments = comments;
    }

    @OneToOne
    private Student student;

    @OneToOne
    private Teacher teacher;

    @OneToOne
    private Subject subject;

    private String comments;
}
