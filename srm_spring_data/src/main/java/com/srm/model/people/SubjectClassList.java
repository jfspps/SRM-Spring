package com.srm.model.people;

import com.srm.model.academic.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SubjectClassList extends StudentList {

    @ManyToMany(mappedBy = "subjectClassLists")
    private Set<Student> studentList = new HashSet<>();

    @OneToOne
    private Teacher teacher;

    @OneToOne
    private Subject subject;        //possible for a teacher to teach one student different subjects (Math / Phys)
}
