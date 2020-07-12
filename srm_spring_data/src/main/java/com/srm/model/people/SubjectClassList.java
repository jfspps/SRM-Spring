package com.srm.model.people;

import com.srm.model.academic.Subject;

import java.util.Set;

public class SubjectClassList extends StudentList {

    private Set<Student> studentList;
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

    public Set<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(Set<Student> studentList) {
        this.studentList = studentList;
    }
}
