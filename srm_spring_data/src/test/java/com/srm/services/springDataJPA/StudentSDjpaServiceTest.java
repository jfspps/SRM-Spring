package com.srm.services.springDataJPA;

import com.srm.model.people.Student;
import com.srm.repositories.peopleRepos.StudentRepository;
import com.srm.services.peopleServices.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

//test Spring Data JPA service with Mockito
@ExtendWith(MockitoExtension.class)
class StudentSDjpaServiceTest {

    //mock object needed for JPA functionality
    @Mock
    StudentRepository studentRepository;

    final String firstName = "James";
    final String lastName = "Apps";
    Long id = 1L;
    Student mockStudent;
    List<Student> studentList = new ArrayList<>();

    //directs Mockito to inject above mocks (effectively a constructor)
    @InjectMocks
    StudentSDjpaService studentSDjpaService;

    @BeforeEach
    void setUp() {
        mockStudent = Student.builder().firstName(firstName).lastName(lastName).build();
    }

    @Test
    void findByLastName() {
        //instruct any* call to findByLastName() to return mockStudent
        when(studentRepository.findByLastName(any())).thenReturn(mockStudent);

        //instantiate a new Student with the same details as mockStudent
        //*demonstrates that last name passed is irrelevant
        Student randomStudent = studentSDjpaService.findByLastName("someOtherDude");

        assertEquals(lastName, randomStudent.getLastName());

        // verify that JPA studentRepository's findByLastName() was called when calling SDjpa's findByLastName() was
        // called (this demonstrates that the mapping provided in SDjpa was successful)
        verify(studentRepository).findByLastName(any());
    }

    @Test
    void findByFirstNameAndLastName() {
        when(studentRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(mockStudent);

        Student testStudent = studentSDjpaService.findByFirstAndLastName("The", "Mole");
        assertNotNull(testStudent);
        assertEquals(lastName, testStudent.getLastName());
        assertEquals(firstName, testStudent.getFirstName());
        verify(studentRepository, times(1)).findByFirstNameAndLastName(anyString(), anyString());
    }

    @Test
    void findAllByLastNameLike(){
        studentList.add(Student.builder().lastName("Jones").build());
        when(studentRepository.findAllByLastNameLike(anyString())).thenReturn(studentList);

        List<Student> testList = studentSDjpaService.findAllByLastNameLike("Jones");
        assertEquals(1, testList.size());
        assertEquals("Jones", testList.get(0).getLastName());
    }

    @Test
    void findAllByLastNameLikePartial(){
        studentList.add(Student.builder().lastName("Jones").build());
        when(studentRepository.findAllByLastNameLike(anyString())).thenReturn(studentList);

        List<Student> testList = studentSDjpaService.findAllByLastNameLike("ones");
        assertEquals(1, testList.size());
        assertEquals("Jones", testList.get(0).getLastName());
    }

    @Test
    void findAllByLastNameLikeIgnoreCase(){
        studentList.add(Student.builder().lastName("Jones").build());
        when(studentRepository.findAllByLastNameLike(anyString())).thenReturn(studentList);

        List<Student> testList = studentSDjpaService.findAllByLastNameLike("JONES");
        assertEquals(1, testList.size());
        assertEquals("Jones", testList.get(0).getLastName());
    }

    //equivalent to findAll()
    @Test
    void findAllByLastNameLikeBlank(){
        studentList.add(Student.builder().lastName("Jones").build());
        when(studentRepository.findAllByLastNameLike(anyString())).thenReturn(studentList);

        List<Student> testList = studentSDjpaService.findAllByLastNameLike("");
        assertEquals(1, testList.size());
        assertEquals("Jones", testList.get(0).getLastName());
    }

    @Test
    void save() {
        Student StudentToBeSaved = Student.builder().id(id).build();

        when(studentRepository.save(any())).thenReturn(mockStudent);

        //savedStudent will be the same as mockStudent; at this stage, we only verify the return of save()
        Student savedStudent = studentSDjpaService.save(StudentToBeSaved);

        assertNotNull(savedStudent);

        verify(studentRepository).save(any());
    }

    @Test
    void findById() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(mockStudent));

        Student studentFound = studentSDjpaService.findById(4L);

        assertNotNull(studentFound);

        verify(studentRepository).findById(anyLong());
    }

    @Test
    void findAll() {
        Set<Student> studentSet = new HashSet<>();
        studentSet.add(Student.builder().id(1L).build());
        studentSet.add(Student.builder().id(2L).build());

        when(studentRepository.findAll()).thenReturn(studentSet);

        Set<Student> returnedSet = studentSDjpaService.findAll();

        assertNotNull(returnedSet);
        assertEquals(2, returnedSet.size());
    }

    @Test
    void findAllWithSave() {
        Student student1 = Student.builder().id(1L).build();
        Student student2 = Student.builder().id(2L).build();

        studentSDjpaService.save(student1);
        studentSDjpaService.save(student2);

        Set<Student> returnedSet = studentSDjpaService.findAll();

        assertNotNull(returnedSet);
        assertEquals(0, returnedSet.size());
        //save() doesn't seem to persist at the moment...
    }

    @Test
    void delete() {
        //test somewhat meaningless for now...

        studentSDjpaService.save(mockStudent);
        assertEquals(0, studentSDjpaService.findAll().size());
        studentSDjpaService.delete(mockStudent);
        assertEquals(0, studentSDjpaService.findAll().size());
        verify(studentRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        //test somewhat meaningless for now...

        studentSDjpaService.save(mockStudent);
        assertEquals(0, studentSDjpaService.findAll().size());
        studentSDjpaService.deleteById(id);
        assertEquals(0, studentSDjpaService.findAll().size());
        verify(studentRepository).deleteById(anyLong());
    }
}