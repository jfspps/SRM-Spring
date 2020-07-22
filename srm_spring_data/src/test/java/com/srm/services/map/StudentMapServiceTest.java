package com.srm.services.map;

import com.srm.model.people.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapServiceTest {

    StudentMapService studentMapService;
    final String firstName = "James";
    final String lastName = "Apps";
    Long id = 1L;

    @BeforeEach
    void setUp() {
        //required for DB extraction tests
        studentMapService = new StudentMapService();
        studentMapService.save(Student.builder().id(id).firstName(firstName).lastName(lastName).build());
    }

    @Test
    void save_withID() {
        //overrides the student with the (same) id = 1L
        Student student_withID = Student.builder().id(id).build();
        assertNotNull(student_withID);

        Student savedStudent = studentMapService.save(student_withID);
        assertNotNull(savedStudent);
        assertEquals(id, savedStudent.getId());
        assertNull(savedStudent.getFirstName());
    }

    @Test
    void save_withoutID() {
        //add another student without an ID and check if save() assigns one automatically
        Student student_withoutID = studentMapService.save(Student.builder().build());
        assertNotNull(student_withoutID);
        assertNotNull(student_withoutID.getId());
    }

    @Test
    void findById() {
        Student student = studentMapService.findById(id);
        assertEquals(id, student.getId());
    }

    @Test
    void findAll() {
        Set<Student> students = studentMapService.findAll();
        assertEquals(1, students.size());
    }

    @Test
    void delete() {
        //remove a student from the initialised set
        studentMapService.delete(studentMapService.findById(id));
        assertEquals(0, studentMapService.findAll().size());
    }

    @Test
    void deleteById() {
        studentMapService.deleteById(id);
        assertEquals(0, studentMapService.findAll().size());
    }

    @Test
    void findByLastName() {
        Student queryStudent = studentMapService.findByLastName(lastName);
        assertEquals(lastName, queryStudent.getLastName());
    }
}