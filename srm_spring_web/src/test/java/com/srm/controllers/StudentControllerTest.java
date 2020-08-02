package com.srm.controllers;

import com.srm.model.people.*;
import com.srm.services.peopleServices.StudentService;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    StudentService studentService;

    @InjectMocks
    StudentController studentController;

    MockMvc mockMvc;
    //prepare DB entries
    Set<Student> studentSet;

    Student testStudent;
    Teacher teacher1;
    Guardian guardian1;
    FormGroupList formGroupList1;
    ContactDetail teacher1Contact;
    Address address1;

    @BeforeEach
    void setUp() {
        studentSet = new HashSet<>();
        studentSet.add(Student.builder().id(1L).build());
        studentSet.add(Student.builder().id(2L).build());

        teacher1Contact = ContactDetail.builder().email("teacher1@school.com").phoneNumber("9847324").build();
        teacher1 = Teacher.builder().firstName("Keith").lastName("Thomson").contactDetail(teacher1Contact).build();
        address1 = Address.builder().firstLine("88 Penine Way").secondLine("Farnborough").postcode("CHG9475JF").build();
        guardian1 = Guardian.builder().firstName("Alan").lastName("Smith").address(address1).build();
        Set<Guardian> guardianGroup = new HashSet<>();
        guardianGroup.add(guardian1);
        Set<Student> studentgroup1 = new HashSet<>();
        formGroupList1 = FormGroupList.builder().studentList(studentgroup1).groupName("Group 1").teacher(teacher1).build();

        testStudent = Student.builder()
                .firstName("Elizabeth")
                .lastName("Jones")
                .contactDetail(teacher1Contact)
                .formGroupList(formGroupList1)
                .personalTutor(teacher1)
                .guardians(guardianGroup)
                .build();

        studentgroup1.add(testStudent);

        //provides each test method with a mock controller based on studentIndexController
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    void listStudents() throws Exception {
        //direct any call to findAll() to use above studentSet instead
        when(studentService.findAll()).thenReturn(studentSet);

        mockMvc.perform(get("/students"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("students/index"))
                .andExpect(model().attribute("students", hasSize(2)));  //two records in findAll() Set
    }

    @Test
    void listStudentsIndex() throws Exception {
        // direct any call to findAll() to use above studentSet instead
        // (there should be no calls to studentService, see below)
        when(studentService.findAll()).thenReturn(studentSet);

        mockMvc.perform(get("/students/index"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("students/index"))
                .andExpect(model().attribute("students", hasSize(2))); //two records in findAll() Set
    }

    @Test
    void listStudentsIndexHTML() throws Exception {
        //direct any call to findAll() to use above studentSet instead
        when(studentService.findAll()).thenReturn(studentSet);

        mockMvc.perform(get("/students/index.html"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("students/index"))
                .andExpect(model().attribute("students", hasSize(2)));  //two records in findAll() Set
    }

    @Test
    void showStudentById() throws Exception{
        when(studentService.findById(anyLong())).thenReturn(testStudent);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("/students/studentDetails"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    void showStudentByIdCheckFirstName() throws Exception{
        when(studentService.findById(anyLong())).thenReturn(testStudent);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("/students/studentDetails"))
                .andExpect(model().attribute("student",
                        hasProperty("firstName", Matchers.is(testStudent.getFirstName()))));
    }

    @Test
    void showStudentByIdCheckTeacher() throws Exception{
        when(studentService.findById(anyLong())).thenReturn(testStudent);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("/students/studentDetails"))
                .andExpect(model().attribute("student",
                        hasProperty("teacher", Matchers.is(testStudent.getTeacher()))));
    }

    @Test
    void showStudentByIdCheckTeacherLastName() throws Exception{
        when(studentService.findById(anyLong())).thenReturn(testStudent);

        assertEquals(teacher1.getLastName(), studentService.findById(23L).getTeacher().getLastName());
    }

    @Test
    void showStudentByIdCheckFormGroupName() throws Exception{
        when(studentService.findById(anyLong())).thenReturn(testStudent);

        assertEquals(formGroupList1.getGroupName(), studentService.findById(23L).getFormGroupList().getGroupName());
    }

    @Test
    void showStudentByIdCheckEmail() throws Exception{
        when(studentService.findById(anyLong())).thenReturn(testStudent);

        assertEquals(teacher1Contact.getEmail(), studentService.findById(23L).getContactDetail().getEmail());
    }

    @Test
    void showStudentByIdCheckContactDetail() throws Exception {
        when(studentService.findById(anyLong())).thenReturn(testStudent);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("/students/studentDetails"))
                .andExpect(model().attribute("student",
                        hasProperty("contactDetail", Matchers.is(teacher1Contact))));
    }

    @Test
    void showStudentByIdCheckNumberOfGuardians() throws Exception{
        when(studentService.findById(anyLong())).thenReturn(testStudent);

        assertEquals(1, studentService.findById(23L).getGuardians().size());
    }

    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get("/students/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("/students/newStudent"))
                .andExpect(model().attributeExists("newStudent"));
    }
}