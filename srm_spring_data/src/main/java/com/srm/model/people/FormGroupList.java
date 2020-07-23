package com.srm.model.people;

import com.srm.model.BaseEntity;
import lombok.Builder;
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
@Entity
public class FormGroupList extends BaseEntity {

    @Builder
    public FormGroupList(Long id, String groupName, Set<Student> studentList, Teacher teacher) {
        super(id);
        this.groupName = groupName;
        this.studentList = studentList;
        this.teacher = teacher;
    }

    private String groupName;

    @OneToMany(mappedBy = "formGroupList")
    private Set<Student> studentList = new HashSet<>();

    @OneToOne
    private Teacher teacher;
}
