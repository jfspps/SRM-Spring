package com.srm.controllers;

import com.srm.exceptions.NotFoundException;
import com.srm.model.academic.Subject;
import com.srm.model.people.Teacher;
import com.srm.services.academicServices.SubjectService;
import com.srm.services.peopleServices.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class SubjectControllerTest {

    @Mock
    SubjectService subjectService;

    @Mock
    TeacherService teacherService;

    @InjectMocks
    SubjectController subjectController;

    MockMvc mockMvc;
    //prepare DB entries
    Set<Subject> subjects = new HashSet<>();

    private final Teacher teacher1 = Teacher.builder().firstName("Jim").lastName("Kirk").build();
    private final Teacher teacher2 = Teacher.builder().firstName("Kathryn").lastName("Janeway").build();
    Set<Teacher> teachers1 = new HashSet<>();
    Set<Teacher> teachers2 = new HashSet<>();

    Subject subject1;
    Subject subject2;

    String[] strings;

    @BeforeEach
    void setUp() {
        teachers1.add(teacher1);
        String subject1name = "History";
        subject1 = Subject.builder().id(1L).subjectName(subject1name).teachers(teachers1).build();
        subjects.add(subject1);
        teachers2.add(teacher2);
        String subject2name = "PE";
        subject2 = Subject.builder().id(2L).subjectName(subject2name).teachers(teachers2).build();
        subjects.add(subject2);

        strings = new String[5];

        //provides each test method with a mock controller based on studentIndexController
        mockMvc = MockMvcBuilders.standaloneSetup(subjectController)
                .setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    void listSubjects() throws Exception {
        when(subjectService.findAll()).thenReturn(subjects);

        mockMvc.perform(get("/subjects"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("/subjects/index"))
                .andExpect(model().attribute("subjects", hasSize(2)));  //two records in findAll() Set
    }

    @Test
    public void testGetSubjectNotFound() throws Exception {

        when(subjectService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/subjects/1"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"))
                .andExpect(model().attributeExists("exception"));
    }

    @Test
    void numberFormatSubjectFindByIdTest() throws Exception{
        mockMvc.perform(get("/subjects/XYxyXYxy"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"))
                .andExpect(model().attributeExists("exception"));
    }

    @Test
    void showSubject() throws Exception {
        when(subjectService.findById(anyLong())).thenReturn(subject1);

        mockMvc.perform(get("/subjects/" + subject1.getId()))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("/subjects/subjectDetails"))
                .andExpect(model().attribute("subject",
                        hasProperty("subjectName", is(subject1.getSubjectName()))));
    }

    @Test
    void initSubjectCreationForm() throws Exception {
        mockMvc.perform(get("/subjects/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("/subjects/newSubject"))
                .andExpect(model().attributeExists("newSubject"));
    }

    @Test
    void processSubjectCreationForm() throws Exception {
        when(subjectService.save(ArgumentMatchers.any())).thenReturn(Subject.builder().id(1L).build());

        //run POST request with subjectName filled in, then check status as subjectService.save() is executed
        mockMvc.perform(post("/subjects/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("subjectName", "some name"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/subjects/index"))
                .andExpect(model().attributeExists("newSubject"));

        verify(subjectService).save(ArgumentMatchers.any());
    }

    @Test
    void processCreationFormValidationFail() throws Exception {

        //run POST request without filling in the form (w/o submitting subjectName) and check validation response
        mockMvc.perform(post("/subjects/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("newSubject"))
                .andExpect(view().name("/subjects/newSubject"));
    }


    @Test
    void initUpdateSubjectForm() throws Exception {
        when(subjectService.findById(anyLong())).thenReturn(Subject.builder().id(1L).build());

        mockMvc.perform(get("/subjects/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("/subjects/updateSubject"))
                .andExpect(model().attributeExists("updateSubject"));
    }

//    @Test
//    void processUpdateSubjectForm() throws Exception {
//        when(subjectService.findById(anyLong())).thenReturn(Subject.builder().id(1L).build());
//        when(subjectService.save(ArgumentMatchers.any())).thenReturn(Subject.builder().build());
//
//        mockMvc.perform(post("/subjects/1/edit")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param("id", "1")
//                .param("subjectName", "some name"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/subjects/index"));
//
//        verify(subjectService).save(ArgumentMatchers.any());
//        verify(subjectService).findById(anyLong());
//    }

//    @Test
//    void processUpdateSubjectFormFailedValidation() throws Exception {
//
//        mockMvc.perform(post("/subjects/1/edit")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
////                .param("id", "1"))
//                .andExpect(status().is3xxRedirection())
////                .andExpect(model().attributeExists("updateSubject"))
//                .andExpect(view().name("redirect:/subjects/index"));
//    }

    @Test
    void initUpdateSubjectSetForm() throws Exception {
        when(teacherService.findById(anyLong())).thenReturn(Teacher.builder().subjects(subjects).build());

        mockMvc.perform(get("/subjects/teacher/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("/subjects/updateSubjectSet"))
                .andExpect(model().attributeExists("subject1"))
                .andExpect(model().attributeExists("subject2"))
                .andExpect(model().attributeExists("teacher"));
    }

//    @Test
//    void processUpdateSubjectSetForm() throws Exception{
//
//        when(teacherService.findById(anyLong())).thenReturn(Teacher.builder().subjects(subjects).build());
//        when(teacherService.save(any())).thenReturn(Teacher.builder().subjects(subjects).build());
//        when(subjectService.findBySubjectName(anyString())).thenReturn(subject1);
//
//        //when() requires mocks as argument; Strings (final class) cannot be mocked
//        when(subject1.getSubjectName().split(",")).thenReturn(strings);
//
//        mockMvc.perform(post("/subjects/teacher/1/edit"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/teachers/1/edit"));
//
//        verify(subjectService).save(any());
//    }
}