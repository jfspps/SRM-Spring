package com.srm.model.people;

import com.srm.model.academic.Subject;

import java.util.List;

public class SubjectClassList {

    private Teacher teacher;
    private Subject subject;        //possible for a teacher to teach one student different subjects (Math / Phys)
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
