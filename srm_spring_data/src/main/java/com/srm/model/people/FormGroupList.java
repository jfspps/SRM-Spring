package com.srm.model.people;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormGroupList extends StudentList {

    private Set<Student> studentList = new HashSet<>();
    private Teacher teacher;
}
