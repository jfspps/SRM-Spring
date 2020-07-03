package com.srm.model.people;

import com.srm.model.people.Student;
import com.srm.model.people.Teacher;

import java.util.List;

public class FormGroupList {

    private Teacher teacher;
    private List<Student> studentList;
    private String groupName;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

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
