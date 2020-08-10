package com.srm.services.map;

import com.srm.exceptions.NotFoundException;
import com.srm.model.academic.Subject;
import com.srm.model.people.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SubjectMapServiceTest {

    SubjectMapService subjectMapService;
    Subject testSubject;
    final String subjectName = "Mathematics";
    final Long id = 1L;
    final Teacher teacher1 = new Teacher();
    final Teacher teacher2 = new Teacher();
    Set<Teacher> teacherSet = new HashSet<>();

    @BeforeEach
    void setUp() {
        subjectMapService = new SubjectMapService();
        teacherSet.add(teacher1);
        teacherSet.add(teacher2);
        testSubject = Subject.builder().id(id).subjectName(subjectName).teachers(teacherSet).build();
        subjectMapService.save(testSubject);
    }

    @Test
    void findBySubjectName() {
        Subject testSubject = subjectMapService.findBySubjectName(subjectName);
        assertEquals(subjectName, testSubject.getSubjectName());
    }

    @Test
    void saveWithId() {
        //overrides the above subject with id = 1L
        Subject anotherSubject = Subject.builder().id(id).build();
        Subject savedSubject = subjectMapService.save(anotherSubject);
        assertEquals(1, subjectMapService.findAll().size());
        assertNull(savedSubject.getSubjectName());
    }

    @Test
    void saveWithoutId() {
        Subject anotherSubject = new Subject();
        subjectMapService.save(anotherSubject);
        assertNotNull(subjectMapService);
        assertEquals(2, subjectMapService.findAll().size());
        System.out.println("Subject without ID has ID: " + anotherSubject.getId());
    }

    @Test
    void findById() {
        Subject output = subjectMapService.findById(id);
        assertEquals(id, output.getId());
    }

    @Test
    void notFoundSubjectByIdTest(){
        Throwable exception = assertThrows(NotFoundException.class, () -> {
            subjectMapService.findById(8L);
        });

        assertEquals("Subject not found with ID: 8", exception.getMessage());
    }

    @Test
    void findAll() {
        Set<Subject> subjects = subjectMapService.findAll();
        assertEquals(1, subjects.size());
    }

    @Test
    void delete() {
        subjectMapService.delete(subjectMapService.findById(id));
        assertEquals(0, subjectMapService.findAll().size());
    }

    @Test
    void deleteById() {
        subjectMapService.deleteById(id);
        assertEquals(0, subjectMapService.findAll().size());
    }
}