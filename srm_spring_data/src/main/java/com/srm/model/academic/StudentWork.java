package com.srm.model.academic;

import com.srm.model.BaseEntity;
import com.srm.model.people.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class StudentWork extends BaseEntity {

    private String title;
    private Integer maxScore;               //boxed Integer can be null; allows for letter grades or no score at all
    private boolean contributor = true;     //does this contribute to an overall end-of-term/semester/year score?

    private Teacher uploader;
    private Subject subject;
    private AssignmentType assignmentType;
}
