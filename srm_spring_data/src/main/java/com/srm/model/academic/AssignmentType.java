package com.srm.model.academic;

import com.srm.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AssignmentType extends BaseEntity {
    //this would be uniformly set by the school admin

    private String assignmentType;

    @OneToMany
    private Set<StudentResult> studentResults = new HashSet<>();
}
