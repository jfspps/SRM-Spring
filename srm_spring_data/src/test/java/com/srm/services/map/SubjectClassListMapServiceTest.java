package com.srm.services.map;

import com.srm.model.academic.Subject;
import com.srm.model.people.Student;
import com.srm.model.people.SubjectClassList;
import com.srm.model.people.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubjectClassListMapServiceTest {

    Student student1 = Student.builder().firstName("Jack").build();
    Student student2 = Student.builder().firstName("Jill").build();
    Teacher teacher = Teacher.builder().firstName("Tom").lastName("Jones").build();
    final String groupName = "The kangaroos";
    Set<Student> students = new HashSet<>();

    final String subjectName = "History";
    Subject subject = Subject.builder().subjectName(subjectName).build();

    SubjectClassList subjectClassList;

    SubjectClassListMapService subjectClassListMapService;

    @BeforeEach
    void setUp() {
        students.add(student1);
        students.add(student2);
        subjectClassList = SubjectClassList.builder()
                .subject(subject)
                .groupName(groupName)
                .teacher(teacher)
                .students(students).build();
        subjectClassListMapService = new SubjectClassListMapService();
        subjectClassListMapService.save(subjectClassList);
    }

    @Test
    void findBySubject() {
        SubjectClassList temp = subjectClassListMapService.findBySubject(subjectName);
        assertEquals(subjectName, temp.getSubject().getSubjectName());
    }

    @Test
    void findByTeacherLastName() {
        SubjectClassList temp = subjectClassListMapService.findByTeacherLastName("Jones");
        assertEquals("Jones", temp.getTeacher().getLastName());
    }

    @Test
    void findByGroupName() {
        SubjectClassList temp = subjectClassListMapService.findByGroupName(groupName);
        assertEquals(groupName, temp.getGroupName());
    }

    @Test
    void findByTeacherFirstAndLastName() {
        SubjectClassList temp = subjectClassListMapService.findByTeacherFirstAndLastName("Tom", "Jones");
        assertEquals("Tom", temp.getTeacher().getFirstName());
        assertEquals("Jones", temp.getTeacher().getLastName());
    }
}