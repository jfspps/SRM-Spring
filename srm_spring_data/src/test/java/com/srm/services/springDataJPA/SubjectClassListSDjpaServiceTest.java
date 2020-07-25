package com.srm.services.springDataJPA;

import com.srm.model.academic.Subject;
import com.srm.model.people.Student;
import com.srm.model.people.SubjectClassList;
import com.srm.model.people.Teacher;
import com.srm.repositories.peopleRepos.SubjectClassListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//see StudentSDjpaServiceTest for commentary
@ExtendWith(MockitoExtension.class)
class SubjectClassListSDjpaServiceTest {

    @Mock
    SubjectClassListRepository subjectClassListRepository;

    Student student1 = Student.builder().firstName("Jack").build();
    Student student2 = Student.builder().firstName("Jill").build();
    Teacher teacher = Teacher.builder().firstName("Tom").lastName("Jones").build();
    final String groupName = "The kangaroos";
    Set<Student> students = new HashSet<>();

    final String subjectName = "History";
    Subject subject = Subject.builder().subjectName(subjectName).build();

    SubjectClassList subjectClassList;

    @InjectMocks
    SubjectClassListSDjpaService subjectClassListSDjpaService;

    @BeforeEach
    void setUp() {
        students.add(student1);
        students.add(student2);
        subjectClassList = SubjectClassList.builder()
                .students(students)
                .teacher(teacher)
                .groupName(groupName)
                .subject(subject).build();
    }

    @Test
    void findBySubject() {
        when(subjectClassListRepository.findBySubject_SubjectName(any())).thenReturn(subjectClassList);

        SubjectClassList found = subjectClassListSDjpaService.findBySubject("football");
        verify(subjectClassListRepository, times(1)).findBySubject_SubjectName(any());
        assertEquals(subjectName, found.getSubject().getSubjectName());
    }

    @Test
    void findByTeacherLastName() {
        when(subjectClassListRepository.findByTeacher_LastName(any())).thenReturn(subjectClassList);

        SubjectClassList found = subjectClassListSDjpaService.findByTeacherLastName("Roger");
        verify(subjectClassListRepository, times(1)).findByTeacher_LastName(any());
        assertEquals(teacher.getLastName(), found.getTeacher().getLastName());
    }

    @Test
    void findByGroupName() {
        when(subjectClassListRepository.findByGroupName(any())).thenReturn(subjectClassList);

        SubjectClassList found = subjectClassListSDjpaService.findByGroupName("Cheese");
        verify(subjectClassListRepository, times(1)).findByGroupName(any());
        assertEquals(groupName, found.getGroupName());
    }

    @Test
    void findByTeacherFirstAndLastName() {
        when(subjectClassListRepository.findByTeacher_FirstNameAndTeacher_LastName(any(), any())).thenReturn(subjectClassList);

        SubjectClassList found = subjectClassListSDjpaService.findByTeacherFirstAndLastName("Roger", "Moore");
        verify(subjectClassListRepository, times(1)).findByTeacher_FirstNameAndTeacher_LastName(any(), any());
        assertEquals(teacher.getLastName(), found.getTeacher().getLastName());
        assertEquals(teacher.getFirstName(), found.getTeacher().getFirstName());
    }
}