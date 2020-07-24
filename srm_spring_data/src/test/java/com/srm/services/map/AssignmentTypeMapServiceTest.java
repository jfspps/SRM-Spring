package com.srm.services.map;

import com.srm.model.academic.AssignmentType;
import com.srm.model.academic.StudentResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentTypeMapServiceTest {

    StudentResult studentResult;
    Set<StudentResult> studentResults;
    AssignmentType exams;

    AssignmentTypeMapService assignmentTypeMapService;
    final String Exam = "Exam";
    final String exam = "exam";
    final String Coursework = "Coursework";


    @BeforeEach
    void setUp() {
        assignmentTypeMapService = new AssignmentTypeMapService();
        studentResult = new StudentResult();
        studentResults = new HashSet<>();
        studentResults.add(studentResult);
        exams = AssignmentType.builder().id(1L).studentResults(studentResults).description(Exam).build();
        assignmentTypeMapService.save(exams);
    }

    @Test
    void findByAssignmentType() {
        AssignmentType test = assignmentTypeMapService.findByDescription(Exam);
        AssignmentType test2 = assignmentTypeMapService.findByDescription(exam);
        assertEquals(Exam, test.getDescription());
        assertNotNull(test2);
    }

    @Test
    void findByFalseAssignmentType() {
        AssignmentType test3 = assignmentTypeMapService.findByDescription(Coursework);
        assertNull(test3);
    }
}