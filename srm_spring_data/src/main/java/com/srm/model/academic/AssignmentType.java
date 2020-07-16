package com.srm.model.academic;

import com.srm.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentType extends BaseEntity {
    //this would be uniformly set by the school admin

    private String assignmentType;
}
