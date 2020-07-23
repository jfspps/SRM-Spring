package com.srm.services.springDataJPA;

import com.srm.model.academic.Subject;
import com.srm.model.people.Teacher;
import com.srm.repositories.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

//see StudentSDjpaServiceTest for commentary
@ExtendWith(MockitoExtension.class)
class TeacherSDjpaServiceTest {

    @Mock
    TeacherRepository teacherRepository;

    @InjectMocks
    TeacherSDjpaService teacherSDjpaService;

    Teacher teacher;
    final String firstName = "John";
    final String lastName = "Smith";
    Set<Subject> subjectSet = new HashSet<>();
    Subject subject1 = new Subject();
    Subject subject2 = new Subject();
    final Long id = 1L;

    @BeforeEach
    void setUp() {
        subjectSet.add(subject1);
        subjectSet.add(subject2);
        teacher = Teacher.builder().id(id).firstName(firstName).lastName(lastName).subjects(subjectSet).build();
    }

    @Test
    void findByLastName() {
        when(teacherRepository.findByLastName(lastName)).thenReturn(teacher);

        Teacher teacherFound = teacherSDjpaService.findByLastName(lastName);
        assertEquals(lastName, teacherFound.getLastName());
        verify(teacherRepository, times(1)).findByLastName(anyString());
    }

    @Test
    void saveWithId() {
        when(teacherRepository.save(any())).thenReturn(teacher);

        Teacher teacherSaved = teacherSDjpaService.save(teacher);
        assertEquals(id, teacherSaved.getId());
        verify(teacherRepository, times(1)).save(any());
        //JPA save() not persistent at the moment
        assertEquals(0, teacherSDjpaService.findAll().size());
    }

    @Test
    void saveWithoutId() {
        when(teacherRepository.save(any())).thenReturn(new Teacher());

        Teacher savedWithoutId = teacherSDjpaService.save(teacher);
        assertNull(savedWithoutId.getLastName());
        assertNotNull(savedWithoutId);
        verify(teacherRepository, times(1)).save(any());
    }

    @Test
    void findById() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));

        Teacher teacherFound = teacherSDjpaService.findById(id);
        assertNotNull(teacherFound);
        verify(teacherRepository, times(1)).findById(anyLong());
    }

    @Test
    void findAll() {
        Set<Teacher> teachers = new HashSet<>();
        teachers.add(new Teacher());
        teachers.add(new Teacher());

        when(teacherRepository.findAll()).thenReturn(teachers);

        Set<Teacher> teachersOnFile = teacherSDjpaService.findAll();
        assertEquals(2, teachersOnFile.size());
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    void delete() {
        // see StudentSDjpaServiceTest
        assertEquals(0, teacherSDjpaService.findAll().size());
        teacherSDjpaService.delete(teacher);
        assertEquals(0, teacherSDjpaService.findAll().size());
    }

    @Test
    void deleteById() {
        // see StudentSDjpaServiceTest
        assertEquals(0, teacherSDjpaService.findAll().size());
        teacherSDjpaService.deleteById(id);
        assertEquals(0, teacherSDjpaService.findAll().size());
    }
}