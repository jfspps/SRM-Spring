package com.srm.model.academic;

import com.srm.model.people.Student;
import com.srm.model.people.Teacher;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResult extends StudentWork {

    private Student student;
    private Teacher teacher;        //allow for different teachers to share the same assignment
    private String score;           //allow for numerical or letter based results
    private String comments;
}
