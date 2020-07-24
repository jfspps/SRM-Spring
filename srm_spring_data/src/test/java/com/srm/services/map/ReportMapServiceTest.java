package com.srm.services.map;

import com.srm.model.academic.Report;
import com.srm.model.academic.Subject;
import com.srm.model.people.Student;
import com.srm.model.people.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportMapServiceTest {

    final String teacherLastName = "Jones";
    final String teacherFirstName = "Tom";
    final String studentFirstName = "John";
    final String studentLastName = "Smith";
    final String subjectName = "English";
    Student student = Student.builder().lastName(studentLastName).firstName(studentFirstName).build();
    Teacher marker = Teacher.builder().firstName(teacherFirstName).lastName(teacherLastName).build();
    Subject subject = Subject.builder().subjectName(subjectName).build();
    final String comments = "Well done";

    Report report;
    ReportMapService reportMapService;
    Report savedReport;

    @BeforeEach
    void setUp() {
        report = Report.builder().id(1L).student(student).teacher(marker).subject(subject).comments(comments).build();
        reportMapService = new ReportMapService();
        savedReport = reportMapService.save(report);
    }

    @Test
    void findByStudentLastName() {
        Report found = reportMapService.findByStudentLastName(studentLastName);
        assertEquals(studentLastName, found.getStudent().getLastName());
    }

    @Test
    void findByStudentFirstAndLastName() {
        Report found = reportMapService.findByStudentFirstAndLastName(studentFirstName, studentLastName);
        assertEquals(studentLastName, found.getStudent().getLastName());
        assertEquals(studentFirstName, found.getStudent().getFirstName());
    }

    @Test
    void findByTeacherLastName() {
        Report found = reportMapService.findByTeacherLastName(teacherLastName);
        assertEquals(teacherLastName, found.getTeacher().getLastName());
    }

    @Test
    void findByTeacherFirstAndLastName() {
        Report found = reportMapService.findByTeacherFirstAndLastName(teacherFirstName, teacherLastName);
        assertEquals(teacherLastName, found.getTeacher().getLastName());
        assertEquals(teacherFirstName, found.getTeacher().getFirstName());
    }

    @Test
    void findBySubject() {
        Report found = reportMapService.findBySubject(subjectName);
        assertEquals(subjectName, found.getSubject().getSubjectName());
    }

    @Test
    void retrieveComments() {
        Report found = reportMapService.findByStudentFirstAndLastName(studentFirstName, studentLastName);
        assertEquals(comments, found.getComments());
    }
}