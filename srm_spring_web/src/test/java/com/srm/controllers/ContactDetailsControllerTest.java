package com.srm.controllers;

import com.srm.model.people.*;
import com.srm.services.peopleServices.GuardianService;
import com.srm.services.peopleServices.StudentService;
import com.srm.services.peopleServices.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(MockitoExtension.class)
class ContactDetailsControllerTest {

    @Mock
    StudentService studentService;

    @Mock
    GuardianService guardianService;

    @Mock
    TeacherService teacherService;

    @InjectMocks
    ContactDetailsController contactDetailsController;

    MockMvc mockMvc;
    ContactDetail contactDetail;
    Guardian guardian;
    Teacher teacher;
    Student student;

    @BeforeEach
    void setUp() {
        contactDetail = ContactDetail.builder().build();
        guardian = Guardian.builder().build();
        student = Student.builder().build();
        teacher = Teacher.builder().build();

        //provides each test method with a mock controller based on studentIndexController
        mockMvc = MockMvcBuilders.standaloneSetup(contactDetailsController).build();
    }

    @Test
    void initCreationFormGuardian() throws Exception{
        //Guardian with ID 1L requests a new address
        when(guardianService.findById(anyLong())).thenReturn(guardian);

        mockMvc.perform(get("/guardians/1/contacts/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("/contacts/newUpdateGuardianContact"))
                .andExpect(model().attributeExists("contact"))
                .andExpect(model().attributeExists("guardian"));
    }

    @Test
    void initCreationFormStudent() throws Exception{
        //Student with ID 1L requests a new address
        when(studentService.findById(anyLong())).thenReturn(student);

        mockMvc.perform(get("/students/1/contacts/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("/contacts/newUpdateStudentContact"))
                .andExpect(model().attributeExists("contact"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    void initCreationFormTeacher() throws Exception{
        //Guardian with ID 1L requests a new address
        when(teacherService.findById(anyLong())).thenReturn(teacher);

        mockMvc.perform(get("/teachers/1/contacts/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("/contacts/newUpdateTeacherContact"))
                .andExpect(model().attributeExists("contact"))
                .andExpect(model().attributeExists("teacher"));
    }
}