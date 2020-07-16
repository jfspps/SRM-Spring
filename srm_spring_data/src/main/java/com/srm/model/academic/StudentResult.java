package com.srm.model.academic;

import com.srm.model.BaseEntity;
import com.srm.model.people.Student;
import com.srm.model.people.Teacher;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class StudentResult extends BaseEntity {

    @OneToOne
    private Student student;

    @OneToOne
    private Teacher teacherMarker;        //allow for different teachers to share the same assignment

    @ManyToOne
    private StudentWork studentWork;

    private String score;           //allow for numerical or letter based results
    private String comments;
}
