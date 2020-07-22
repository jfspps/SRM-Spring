package com.srm.controllers;

import com.srm.model.people.Student;
import com.srm.services.StudentService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class StudentIndexControllerTest {

    @Mock
    StudentService studentService;

    @InjectMocks
    StudentIndexController studentIndexController;

    MockMvc mockMvc;
    //prepare DB entries
    Set<Student> studentSet;

    @BeforeEach
    void setUp() {
        studentSet = new HashSet<>();
        studentSet.add(Student.builder().id(1L).build());
        studentSet.add(Student.builder().id(2L).build());

        //provides each test method with a mock controller based on studentIndexController
        mockMvc = MockMvcBuilders.standaloneSetup(studentIndexController).build();
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
}