package com.srm.services.map;

import com.srm.model.academic.StudentResult;
import com.srm.model.academic.StudentWork;
import com.srm.model.academic.Subject;
import com.srm.model.people.Student;
import com.srm.model.people.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentResultMapServiceTest {

    final String teacherLastName = "Jones";
    final String teacherFirstName = "Tom";
    final String studentFirstName = "John";
    final String studentLastName = "Smith";
    final String subjectName = "English";
    Student student = Student.builder().lastName(studentLastName).firstName(studentFirstName).build();
    Teacher marker = Teacher.builder().firstName(teacherFirstName).lastName(teacherLastName).build();
    Teacher setter = Teacher.builder().build();
    Subject subject = Subject.builder().subjectName(subjectName).build();

    final String assignmentTitle = "The merits of testing";
    StudentWork exam = StudentWork.builder().teacherUploader(setter).subject(subject).title(assignmentTitle).build();
    final String score = "MERIT";
    final String comments = "A very pleasing start";

    StudentResult studentResult;
    StudentResultMapService studentResultMapService;

    @BeforeEach
    void setUp() {
        studentResultMapService = new StudentResultMapService();
        studentResult = StudentResult.builder()
                .student(student)
                .teacherMarker(marker)
                .studentWork(exam)
                .score(score)
                .comments(comments).build();
        studentResultMapService.save(studentResult);
    }

    @Test
    void findByStudentLastName() {
        StudentResult found = studentResultMapService.findByStudentLastName(studentLastName);
        assertEquals(studentLastName, found.getStudent().getLastName());
    }

    @Test
    void findByTeacherLastName() {
        StudentResult found = studentResultMapService.findByTeacherLastName(teacherLastName);
        assertEquals(teacherLastName, found.getTeacher().getLastName());
    }

    @Test
    void findByStudentFirstAndLastName() {
        StudentResult found = studentResultMapService.findByStudentFirstAndLastName(studentFirstName, studentLastName);
        assertEquals(studentLastName, found.getStudent().getLastName());
        assertEquals(studentFirstName, found.getStudent().getFirstName());
    }

    @Test
    void findByTeacherFirstAndLastName() {
        StudentResult found = studentResultMapService.findByTeacherFirstAndLastName(teacherFirstName, teacherLastName);
        assertEquals(teacherLastName, found.getTeacher().getLastName());
        assertEquals(teacherFirstName, found.getTeacher().getFirstName());
    }

    @Test
    void findByStudentWorkTitle() {
        StudentResult found = studentResultMapService.findByStudentWorkTitle(assignmentTitle);
        assertEquals(assignmentTitle, found.getStudentWork().getTitle());
    }

    @Test
    void findByScore() {
        StudentResult found = studentResultMapService.findByScore(score);
        assertEquals(score, found.getScore());
    }
}