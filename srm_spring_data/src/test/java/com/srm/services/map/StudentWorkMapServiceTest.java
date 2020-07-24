package com.srm.services.map;

import com.srm.model.academic.AssignmentType;
import com.srm.model.academic.StudentWork;
import com.srm.model.academic.Subject;
import com.srm.model.people.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentWorkMapServiceTest {


    final String teacherLastName = "Jones";
    final String teacherFirstName = "Tom";

    //needed for StudentWork (contributor is set to true by default)
    Teacher setter = Teacher.builder().firstName(teacherFirstName).lastName(teacherLastName).build();
    final String subjectName = "English";
    Subject subject = Subject.builder().subjectName(subjectName).build();
    final int maxScore = 54;
    final String description = "End of term exam";
    AssignmentType assignmentType = AssignmentType.builder().description(description).build();
    final String assignmentTitle = "The merits of testing";

    StudentWork exam;

    StudentWorkMapService studentWorkMapService;

    @BeforeEach
    void setUp() {
        exam = StudentWork.builder()
                .teacherUploader(setter)
                .subject(subject)
                .maxScore(maxScore)
                .assignmentType(assignmentType)
                .title(assignmentTitle).build();
        studentWorkMapService = new StudentWorkMapService();
        studentWorkMapService.save(exam);
    }

    @Test
    void findByTitle() {
        StudentWork found = studentWorkMapService.findByTitle(assignmentTitle);
        assertEquals(assignmentTitle, found.getTitle());
    }

    @Test
    void findByTeacherLastName() {
        StudentWork found = studentWorkMapService.findByTeacherLastName(teacherLastName);
        assertEquals(teacherLastName, found.getTeacherUploader().getLastName());
    }

    @Test
    void findByTeacherFirstAndLastName() {
        StudentWork found = studentWorkMapService.findByTeacherFirstAndLastName(teacherFirstName, teacherLastName);
        assertEquals(teacherLastName, found.getTeacherUploader().getLastName());
        assertEquals(teacherFirstName, found.getTeacherUploader().getFirstName());
    }

    @Test
    void findBySubject() {
        StudentWork found = studentWorkMapService.findBySubject(subject.getSubjectName());
        assertEquals(subjectName, found.getSubject().getSubjectName());
    }

    @Test
    void findByDescription() {
        StudentWork found = studentWorkMapService.findByDescription(assignmentType.getDescription());
        assertEquals(description, found.getAssignmentType().getDescription());
    }

    //test defaults (true)
    @Test
    void findByContribution() {
        StudentWork found = studentWorkMapService.findByContribution(true);
        assertTrue(found.isContributor());
    }

    @Test
    void findByContributionFalse() {
        StudentWork found = studentWorkMapService.findByContribution(true);
        found.setContributor(false);
        assertFalse(found.isContributor());
    }
}