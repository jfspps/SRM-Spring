package com.srm.services.map;

import com.srm.model.people.FormGroupList;
import com.srm.model.people.Student;
import com.srm.model.people.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FormGroupListMapServiceTest {

    Student student1 = Student.builder().firstName("Jack").build();
    Student student2 = Student.builder().firstName("Jill").build();
    Teacher teacher = Teacher.builder().firstName("Tom").lastName("Jones").build();
    final String groupName = "The kangaroos";
    Set<Student> students = new HashSet<>();

    FormGroupList kangaroos;
    FormGroupListMapService formGroupListMapService;

    @BeforeEach
    void setUp() {
        students.add(student1);
        students.add(student2);
        kangaroos = FormGroupList.builder().studentList(students)
                .groupName(groupName)
                .teacher(teacher)
                .id(1L).build();
        formGroupListMapService = new FormGroupListMapService();
    }

    @Test
    void saveWithId() {
        formGroupListMapService.save(kangaroos);
        assertEquals(1, formGroupListMapService.findAll().size());
        assertEquals(1L, kangaroos.getId());
    }

    @Test
    void saveWithoutId() {
        FormGroupList noIdGroup = FormGroupList.builder().groupName("Falcons").build();
        FormGroupList savedGroup = formGroupListMapService.save(noIdGroup);
        assertNotNull(savedGroup);
        assertNotNull(savedGroup.getId());
        System.out.println("Save() handles non-Id objects, id: " + savedGroup.getId());
    }

    @Test
    void findById() {
        formGroupListMapService.save(kangaroos);
        FormGroupList found = formGroupListMapService.findById(1L);
        assertEquals(1L, found.getId());
    }

    @Test
    void findAll() {
        formGroupListMapService.save(kangaroos);
        Set<FormGroupList> formGroupLists = formGroupListMapService.findAll();
        assertEquals(1, formGroupLists.size());
    }

    @Test
    void delete() {
        formGroupListMapService.save(kangaroos);
        assertEquals(1, formGroupListMapService.findAll().size());
        formGroupListMapService.delete(kangaroos);
        assertEquals(0, formGroupListMapService.findAll().size());
    }

    @Test
    void deleteById() {
        formGroupListMapService.save(kangaroos);
        assertEquals(1, formGroupListMapService.findAll().size());
        formGroupListMapService.deleteById(1L);
        assertEquals(0, formGroupListMapService.findAll().size());
    }

    @Test
    void findByGroupName() {
        formGroupListMapService.save(kangaroos);
        FormGroupList found = formGroupListMapService.findByGroupName(groupName);
        assertEquals(groupName, found.getGroupName());
    }

    @Test
    void findByTeacherLastName() {
        formGroupListMapService.save(kangaroos);
        FormGroupList found = formGroupListMapService.findByTeacherLastName("Jones");
        assertEquals("Jones", found.getTeacher().getLastName());
    }

    @Test
    void findByTeacherFirstAndLastName() {
        formGroupListMapService.save(kangaroos);
        FormGroupList found = formGroupListMapService.findByTeacherFirstAndLastName("Tom", "Jones");
        assertEquals("Jones", found.getTeacher().getLastName());
        assertEquals("Tom", found.getTeacher().getFirstName());
    }
}