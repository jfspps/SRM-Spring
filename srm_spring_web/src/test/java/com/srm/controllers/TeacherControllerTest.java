package com.srm.controllers;

import com.srm.model.academic.Subject;
import com.srm.model.people.ContactDetail;
import com.srm.model.people.Teacher;
import com.srm.services.peopleServices.TeacherService;
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

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TeacherControllerTest {

    @Mock
    TeacherService teacherService;

    @InjectMocks
    TeacherController teacherController;

    Teacher testTeacher;
    Subject subject;
    ContactDetail contactDetail;
    Set<Teacher> teacherSet;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        subject = Subject.builder().subjectName("Java").build();
        Set<Subject> subjects = new HashSet<>();
        subjects.add(subject);
        contactDetail = ContactDetail.builder().email("myemail@example.com").phoneNumber("0973409").build();
        testTeacher = Teacher.builder().firstName("Tina").lastName("Watford").subjects(subjects).contactDetail(contactDetail).build();
        teacherSet = new HashSet<>();
        teacherSet.add(testTeacher);

        //provides each test method with a mock controller based on studentIndexController
        mockMvc = MockMvcBuilders.standaloneSetup(teacherController).build();
        }

    @Test
    void listTeachers() throws Exception{
        when(teacherService.findAll()).thenReturn(teacherSet);

        mockMvc.perform(get("/teachers/index"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/index"))
                .andExpect(model().attribute("teachers", hasSize(1)));  //one record in findAll() Set
    }

    @Test
    void index() throws Exception{
        when(teacherService.findAll()).thenReturn(teacherSet);

        mockMvc.perform(get("/teachers"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/index"))
                .andExpect(model().attribute("teachers", hasSize(1)));  //one record in findAll() Set
    }

    @Test
    void indexHTML() throws Exception{
        when(teacherService.findAll()).thenReturn(teacherSet);

        mockMvc.perform(get("/teachers.html"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/index"))
                .andExpect(model().attribute("teachers", hasSize(1)));  //one record in findAll() Set
    }

    @Test
    void showTeacher() throws Exception {
        when(teacherService.findById(anyLong())).thenReturn(testTeacher);

        mockMvc.perform(get("/teachers/1"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("/teachers/teacherDetails"))
                .andExpect(model().attributeExists("teacher"));
    }

    @Test
    void showTeacherCheckSubject() throws Exception {
        when(teacherService.findById(anyLong())).thenReturn(testTeacher);

        mockMvc.perform(get("/teachers/1"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("/teachers/teacherDetails"))
                .andExpect(model().attribute("teacher",
                        hasProperty("contactDetail", is(testTeacher.getContactDetail()))));
    }
}