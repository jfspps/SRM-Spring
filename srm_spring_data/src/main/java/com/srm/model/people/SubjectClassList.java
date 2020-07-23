package com.srm.model.people;

import com.srm.model.BaseEntity;
import com.srm.model.academic.Subject;
import lombok.Builder;
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
@Entity
public class SubjectClassList extends BaseEntity {

    @Builder
    public SubjectClassList(Long id, String groupName, Set<Student> students, Teacher teacher, Subject subject) {
        super(id);
        this.groupName = groupName;
        this.studentList = students;
        this.teacher = teacher;
        this.subject = subject;
    }

    private String groupName;

    @ManyToMany(mappedBy = "subjectClassLists")
    private Set<Student> studentList = new HashSet<>();

    @OneToOne
    private Teacher teacher;

    @OneToOne
    private Subject subject;        //possible for a teacher to teach one student different subjects (Math / Phys)
}
