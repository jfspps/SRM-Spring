package com.srm.model.academic;

import com.srm.model.BaseEntity;
import com.srm.model.people.Student;
import com.srm.model.people.Teacher;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Report extends BaseEntity {

    @OneToOne
    private Student student;

    @OneToOne
    private Teacher teacher;

    @OneToOne
    private Subject subject;
    private String comments;
}
