package com.srm.model.people;

import java.util.HashSet;
import java.util.Set;

public class FormGroupList extends StudentList {

    private Set<Student> studentList = new HashSet<>();
    private Teacher teacher;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Set<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(Set<Student> studentList) {
        this.studentList = studentList;
    }
}
