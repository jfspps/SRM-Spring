package com.srm.model.academic;

import com.srm.model.BaseEntity;
import com.srm.model.people.Student;
import com.srm.model.people.Teacher;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report extends BaseEntity {

    private Student student;
    private Teacher teacher;
    private Subject subject;
    private String comments;
}
