package com.srm.model.people;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FormGroupList extends StudentList {

    @OneToMany(mappedBy = "formGroupList")
    private Set<Student> studentList = new HashSet<>();

    @OneToOne
    private Teacher teacher;
}
