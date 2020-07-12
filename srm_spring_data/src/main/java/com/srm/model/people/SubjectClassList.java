package com.srm.model.people;

import com.srm.model.academic.Subject;

import java.util.List;

public class SubjectClassList extends StudentList {

    private List<Student> studentList;
    private Teacher teacher;
    private Subject subject;        //possible for a teacher to teach one student different subjects (Math / Phys)

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}
