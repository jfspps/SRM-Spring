package com.srm.controllers;

import com.srm.exceptions.NotFoundException;
import com.srm.model.people.Address;
import com.srm.model.people.ContactDetail;
import com.srm.model.people.Guardian;
import com.srm.model.people.Student;
import com.srm.services.peopleServices.GuardianService;
import com.srm.services.peopleServices.StudentService;
import org.hamcrest.Matchers;
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

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class GuardianControllerTest {

    @Mock
    GuardianService guardianService;

    @Mock
    StudentService studentService;

    @InjectMocks
    GuardianController guardianController;

    Student daughter;
    Set<Student> kids;
    Address address;
    ContactDetail contactDetail;

    Set<Guardian> guardians_1;  //size 1
    Set<Guardian> guardians_2;  //size 2
    Guardian testGuardian;
    Guardian anotherTestGuardian;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        daughter = Student.builder().build();
        kids = new HashSet<>();
        kids.add(daughter);
        address = Address.builder().build();
        contactDetail = ContactDetail.builder().build();
        testGuardian = Guardian.builder().address(address).students(kids).contactDetail(contactDetail).firstName("Eric").id(1L).build();
        anotherTestGuardian = Guardian.builder().address(address).students(kids).contactDetail(contactDetail).firstName("Joan").id(2L).build();
        guardians_1 = new HashSet<>();
        guardians_1.add(testGuardian);
        guardians_2 = new HashSet<>();
        guardians_2.add(testGuardian);
        guardians_2.add(anotherTestGuardian);
        //provides each test method with a mock controller based on studentIndexController
        mockMvc = MockMvcBuilders.standaloneSetup(guardianController)
                .setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    void listGuardians() throws Exception {
        when(guardianService.findAll()).thenReturn(guardians_1);

        mockMvc.perform(get("/guardians"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("guardians/index"))
                .andExpect(model().attribute("guardians", hasSize(1)));
    }

    @Test
    void index() throws Exception {
        when(guardianService.findAll()).thenReturn(guardians_1);

        mockMvc.perform(get("/guardians/index"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("guardians/index"))
                .andExpect(model().attribute("guardians", hasSize(1)));
    }

    @Test
    void indexHTML() throws Exception {
        when(guardianService.findAll()).thenReturn(guardians_1);

        mockMvc.perform(get("/guardians/index.html"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("guardians/index"))
                .andExpect(model().attribute("guardians", hasSize(1)));
    }

    @Test
    public void testGetGuardianNotFound() throws Exception {

        when(guardianService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/guardians/1"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"))
                .andExpect(model().attributeExists("exception"));
    }

    @Test
    void numberFormatTeacherFindByIdTest() throws Exception{
        mockMvc.perform(get("/guardians/oorroorrjfjjf"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"))
                .andExpect(model().attributeExists("exception"));
    }

    @Test
    void showGuardianById() throws Exception {
        when(guardianService.findById(anyLong())).thenReturn(testGuardian);

        mockMvc.perform(get("/guardians/1"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("/guardians/guardianDetails"))
                .andExpect(model().attributeExists("guardian"));
    }

    @Test
    void showGuardianByIdCheckFirstName() throws Exception{
        when(guardianService.findById(anyLong())).thenReturn(testGuardian);

        mockMvc.perform(get("/guardians/1"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("/guardians/guardianDetails"))
                .andExpect(model().attribute("guardian",
                        hasProperty("firstName", Matchers.is(testGuardian.getFirstName()))));
    }

    @Test
    void showGuardianByIdCheckStudent() throws Exception{
        when(guardianService.findById(anyLong())).thenReturn(testGuardian);

        mockMvc.perform(get("/guardians/1"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("/guardians/guardianDetails"))
                .andExpect(model().attribute("guardian",
                        hasProperty("students", Matchers.is(kids))));
    }

    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get("/guardians/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("/guardians/newGuardian"))
                .andExpect(model().attributeExists("guardian"));
    }

//    @Test
//    void processCreationForm() throws Exception {
//        //todo re-test after form validation
//        when(guardianService.save(ArgumentMatchers.any())).thenReturn(Guardian.builder().id(1L).build());
//
//        mockMvc.perform(post("/guardians/new"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/guardians/1"))
//                .andExpect(model().attributeExists("guardian"));
//
//        verify(guardianService).save(ArgumentMatchers.any());
//    }

    @Test
    void initUpdateGuardianForm() throws Exception {
        when(guardianService.findById(anyLong())).thenReturn(Guardian.builder().id(1L).build());

        mockMvc.perform(get("/guardians/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("/guardians/updateGuardian"))
                .andExpect(model().attributeExists("guardian"));
    }

    @Test
    void processUpdateGuardianForm() throws Exception {
        when(guardianService.save(ArgumentMatchers.any())).thenReturn(Guardian.builder().id(1L).build());
        when(guardianService.findById(anyLong())).thenReturn(Guardian.builder().id(1L).build());

        mockMvc.perform(post("/guardians/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/guardians/1"))
                .andExpect(model().attributeExists("guardian"));

        verify(guardianService).save(ArgumentMatchers.any());
        verify(guardianService).findById(anyLong());
    }

    @Test
    void initUpdateGuardianSetForm_emptyGuardianSet() throws Exception {
        when(studentService.findById(anyLong())).thenReturn(Student.builder().guardians(new HashSet<>()).build());

        mockMvc.perform(get("/guardians/student/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("/students/updateGuardianSet"))
                .andExpect(model().attributeExists("guardian1"))
                .andExpect(model().attributeExists("guardian2"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    void initUpdateGuardianSetForm_twoGuardianSet() throws Exception {
        when(studentService.findById(anyLong())).thenReturn(Student.builder().guardians(guardians_2).build());

        mockMvc.perform(get("/guardians/student/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("/students/updateGuardianSet"))
                .andExpect(model().attributeExists("guardian1"))
                .andExpect(model().attributeExists("guardian2"))
                .andExpect(model().attributeExists("student"));
    }

    //no mock test for processStudentSetForm()
}