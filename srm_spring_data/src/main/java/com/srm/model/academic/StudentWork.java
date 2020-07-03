package com.srm.model.academic;

import com.srm.model.BaseEntity;
import com.srm.model.people.Teacher;

public class StudentWork extends BaseEntity {

    private String title;
    private Integer maxScore;               //boxed Integer can be null; allows for letter grades or no score at all
    private boolean contributor = true;     //does this contribute to an overall end-of-term/semester/year score?

    private Teacher uploader;
    private Subject subject;
    private AssignmentType assignmentType;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public boolean isContributor() {
        return contributor;
    }

    public void setContributor(boolean contributor) {
        this.contributor = contributor;
    }

    public Teacher getUploader() {
        return uploader;
    }

    public void setUploader(Teacher uploader) {
        this.uploader = uploader;
    }

    public AssignmentType getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(AssignmentType assignmentType) {
        this.assignmentType = assignmentType;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
