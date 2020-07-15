package com.srm.model.people;

import com.srm.model.academic.Subject;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectClassList extends StudentList {

    private Set<Student> studentList;
    private Teacher teacher;
    private Subject subject;        //possible for a teacher to teach one student different subjects (Math / Phys)
}
