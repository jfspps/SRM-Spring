package com.srm.controllers;

import com.srm.model.academic.Subject;
import com.srm.model.people.ContactDetail;
import com.srm.model.people.Teacher;
import com.srm.services.peopleServices.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get("/teachers/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("/teachers/newTeacher"))
                .andExpect(model().attributeExists("teacher"));
    }

    @Test
    void processCreationForm() throws Exception {
//        //todo re-test after form validation
//        when(studentService.save(ArgumentMatchers.any())).thenReturn(Student.builder().id(1L).build());
//
//        mockMvc.perform(post("/students/new"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/students/1"))
//                .andExpect(model().attributeExists("student"));
//
//        verify(studentService).save(ArgumentMatchers.any());
    }

    @Test
    void initUpdateTeacherForm() throws Exception {
        when(teacherService.findById(anyLong())).thenReturn(Teacher.builder().id(1l).build());

        mockMvc.perform(get("/teachers/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("/teachers/updateTeacher"))
                .andExpect(model().attributeExists("teacher"));
    }

    @Test
    void processUpdateTeacherForm() throws Exception {
        when(teacherService.save(ArgumentMatchers.any())).thenReturn(Teacher.builder().id(1l).build());

        mockMvc.perform(post("/teachers/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/teachers/1"))
                .andExpect(model().attributeExists("teacher"));

        verify(teacherService).save(ArgumentMatchers.any());
    }
}