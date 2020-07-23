package com.srm.services.map;

import com.srm.model.academic.Subject;
import com.srm.model.people.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TeacherMapServiceTest {

    TeacherMapService teacherMapService;
    Teacher teacher;
    final String firstName = "John";
    final String lastName = "Smith";
    Set<Subject> subjectSet = new HashSet<>();
    Subject subject1 = new Subject();
    Subject subject2 = new Subject();
    final Long id = 1L;

    @BeforeEach
    void setUp() {
        teacherMapService = new TeacherMapService();
        subjectSet.add(subject1);
        subjectSet.add(subject2);
        teacher = Teacher.builder().id(id).firstName(firstName).lastName(lastName).subjects(subjectSet).build();
        teacherMapService.save(teacher);
    }

    @Test
    void saveWithId() {
        //overrides the above teacher with id = 1L
        Teacher teacherWithId = Teacher.builder().id(id).build();
        Teacher savedTeacher = teacherMapService.save(teacherWithId);
        assertEquals(id, savedTeacher.getId());
        assertEquals(1, teacherMapService.findAll().size());
        assertNull(savedTeacher.getFirstName());
    }

    @Test
    void saveWithoutId() {
        Teacher teacherWithoutId = new Teacher();
        Teacher savedTeacher = teacherMapService.save(teacherWithoutId);
        assertNotNull(teacherWithoutId);
        assertEquals(id + 1, savedTeacher.getId());
    }

    @Test
    void findById() {
        Teacher found = teacherMapService.findById(id);
        assertEquals(id, found.getId());
    }

    @Test
    void findAll() {
        Set<Teacher> teachers = teacherMapService.findAll();
        assertEquals(1, teachers.size());
    }

    @Test
    void delete() {
        teacherMapService.delete(teacherMapService.findAll().iterator().next());
        assertEquals(0, teacherMapService.findAll().size());
    }

    @Test
    void deleteById() {
        teacherMapService.deleteById(id);
        assertEquals(0, teacherMapService.findAll().size());
    }

    @Test
    void findByLastName() {
        Teacher found = teacherMapService.findByLastName(lastName);
        assertEquals(lastName, found.getLastName());
    }
}