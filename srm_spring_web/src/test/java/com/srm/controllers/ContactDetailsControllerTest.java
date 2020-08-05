package com.srm.controllers;

import com.srm.model.people.*;
import com.srm.services.peopleServices.ContactDetailService;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Mock
    ContactDetailService contactDetailService;

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
        guardian = Guardian.builder().id(2L).build();
        student = Student.builder().id(2L).build();
        teacher = Teacher.builder().id(2L).build();

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

    @Test
    void processCreationFormGuardian() throws Exception{
        //2L is needed for routing

        when(guardianService.findById(anyLong())).thenReturn(guardian);

        mockMvc.perform(post("/guardians/2/contacts/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/guardians/2/edit"))
                .andDo(MockMvcResultHandlers.print());

        verify(contactDetailService).save(any());
    }

    @Test
    void processCreationFormStudent() throws Exception{
        // 2L is needed for routing
        when(studentService.findById(anyLong())).thenReturn(student);

        mockMvc.perform(post("/students/2/contacts/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/students/2/edit"));

        verify(contactDetailService).save(any());
    }

    @Test
    void processCreationFormTeacher() throws Exception{
        // 2L is needed for routing
        when(teacherService.findById(anyLong())).thenReturn(teacher);

        mockMvc.perform(post("/teachers/2/contacts/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/teachers/2/edit"));

        verify(contactDetailService).save(any());
    }

    @Test
    void initUpdateFormGuardian() throws Exception {
        when(guardianService.findById(anyLong())).thenReturn(guardian);

        mockMvc.perform(get("/guardians/2/contacts/3/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("/contacts/newUpdateGuardianContact"))
                .andExpect(model().attributeExists("guardian"));
    }

    @Test
    void processUpdateFormGuardian() throws Exception {
        when(guardianService.findById(anyLong())).thenReturn(guardian);

        mockMvc.perform(post("/guardians/2/contacts/3/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/guardians/2/edit"));
        verify(contactDetailService).save(any());
    }

    @Test
    void initUpdateFormStudent() throws Exception {
        when(studentService.findById(anyLong())).thenReturn(student);

        mockMvc.perform(get("/students/2/contacts/3/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("/contacts/newUpdateStudentContact"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    void processUpdateFormStudent() throws Exception {
        when(studentService.findById(anyLong())).thenReturn(student);

        mockMvc.perform(post("/students/2/contacts/3/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/students/2/edit"));
        verify(contactDetailService).save(any());
    }

    @Test
    void initUpdateFormTeacher() throws Exception {
        when(teacherService.findById(anyLong())).thenReturn(teacher);

        mockMvc.perform(get("/teachers/2/contacts/3/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("/contacts/newUpdateTeacherContact"))
                .andExpect(model().attributeExists("teacher"));
    }

    @Test
    void processUpdateFormTeacher() throws Exception {
        when(teacherService.findById(anyLong())).thenReturn(teacher);

        mockMvc.perform(post("/teachers/2/contacts/3/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/teachers/2/edit"));
        verify(contactDetailService).save(any());
    }
}