package com.srm.model.academic;

import com.srm.model.BaseEntity;

public class Subject extends BaseEntity {

    private String subjectName;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
