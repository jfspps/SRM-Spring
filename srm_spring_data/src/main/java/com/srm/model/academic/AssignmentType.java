package com.srm.model.academic;

import com.srm.model.BaseEntity;
import lombok.Builder;
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
@Entity
public class AssignmentType extends BaseEntity {
    //this would be uniformly set by the school admin

    @Builder
    public AssignmentType(Long id, String description, Set<StudentResult> studentResults) {
        super(id);
        this.description = description;
        this.studentResults = studentResults;
    }

    private String description;

    @OneToMany
    private Set<StudentResult> studentResults = new HashSet<>();
}
