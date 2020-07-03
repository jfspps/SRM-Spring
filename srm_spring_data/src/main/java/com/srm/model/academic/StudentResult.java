package com.srm.model.academic;

import com.srm.model.people.Student;
import com.srm.model.people.Teacher;

public class StudentResult extends StudentWork{

    private Student student;
    private Teacher teacher;        //allow for different teachers to share the same assignment
    private String score;           //allow for numerical or letter based results
    private String comments;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
