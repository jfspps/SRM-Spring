package com.srm.model.people;

import com.srm.model.BaseEntity;

import java.util.List;

public class StudentList extends BaseEntity {

    private List<Student> studentList;
    private String groupName;

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
