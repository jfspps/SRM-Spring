package com.srm.services.map;

import com.srm.model.people.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
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

    @Test
    void findByFirstAndLastName() {
        Student studentFullName = studentMapService.findByFirstAndLastName(firstName, lastName);
        assertEquals(lastName, studentFullName.getLastName());
        assertEquals(firstName, studentFullName.getFirstName());
    }

    @Test
    void findByBlankFirstOnlyLastName() {
        Student saved = studentMapService.save(Student.builder().firstName("").lastName(lastName).build());

        Student studentFullName = studentMapService.findByFirstAndLastName("", lastName);
        assertEquals(saved, studentFullName);
    }



    @Test
    void findByLastNameLike(){
        List<Student> testList = studentMapService.findAllByLastNameLike(lastName);
        assertEquals(lastName, testList.get(0).getLastName());
    }

    @Test
    void findByLastNameLikeIgnoreCase(){
        List<Student> testList = studentMapService.findAllByLastNameLike(lastName.toUpperCase());
        assertEquals(lastName, testList.get(0).getLastName());
    }

    //equivalent to findAll()
    @Test
    void findByLastNameLikeBlank(){
        List<Student> testList = studentMapService.findAllByLastNameLike("");
        assertEquals(lastName, testList.get(0).getLastName());
    }

    @Test
    void findByLastNameLikePartial(){
        List<Student> testList = studentMapService.findAllByLastNameLike("app");
        assertEquals(lastName, testList.get(0).getLastName());
    }

    @Test
    void findAllByFirstNameLikeAndLastNameLike(){
        List<Student> testList = studentMapService.findAllByFirstNameLikeAndLastNameLike(firstName, lastName);
        assertEquals(firstName, testList.get(0).getFirstName());
        assertEquals(lastName, testList.get(0).getLastName());
    }

    @Test
    void findAllByFirstNameLikeAndLastNameLikeBlank(){
        List<Student> testList = studentMapService.findAllByFirstNameLikeAndLastNameLike("", "");
        assertEquals(firstName, testList.get(0).getFirstName());
        assertEquals(lastName, testList.get(0).getLastName());
    }

    @Test
    void findAllByFirstNameLikeAndLastNameLikeFirstNameBlank(){
        List<Student> testList = studentMapService.findAllByFirstNameLikeAndLastNameLike("", lastName);
        assertEquals(firstName, testList.get(0).getFirstName());
        assertEquals(lastName, testList.get(0).getLastName());
    }

    @Test
    void findAllByFirstNameLikeAndLastNameLikeLastNameBlank(){
        List<Student> testList = studentMapService.findAllByFirstNameLikeAndLastNameLike(firstName, "");
        assertEquals(firstName, testList.get(0).getFirstName());
        assertEquals(lastName, testList.get(0).getLastName());
    }

    @Test
    void findAllByFirstNameLikeAndLastNameLikeBothPartials(){
        List<Student> testList = studentMapService.findAllByFirstNameLikeAndLastNameLike("jam", "pps");
        assertEquals(firstName, testList.get(0).getFirstName());
        assertEquals(lastName, testList.get(0).getLastName());
    }

    @Test
    void findAllByFirstNameLikeAndLastNameLikeOnePartialOneBlank(){
        List<Student> testList = studentMapService.findAllByFirstNameLikeAndLastNameLike("", "pps");
        assertEquals(firstName, testList.get(0).getFirstName());
        assertEquals(lastName, testList.get(0).getLastName());
    }
}