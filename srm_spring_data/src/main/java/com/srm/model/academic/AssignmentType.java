package com.srm.model.academic;

import com.srm.model.BaseEntity;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentType extends BaseEntity {
    //this would be uniformly set by the school admin

    private String assignmentType;
}
