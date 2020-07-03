package com.srm.model.academic;

import com.srm.model.BaseEntity;

public class AssignmentType extends BaseEntity {
    //this would be uniformly set by the school admin

    private String assignmentType;

    public String getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(String assignmentType) {
        this.assignmentType = assignmentType;
    }
}
