package com.srm.controllers;

import com.srm.model.people.Address;
import com.srm.model.people.ContactDetail;
import com.srm.model.people.Guardian;
import com.srm.model.people.Student;
import com.srm.services.peopleServices.GuardianService;
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

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class GuardianControllerTest {

    @Mock
    GuardianService guardianService;

    @InjectMocks
    GuardianController guardianController;

    Student daughter;
    Set<Student> kids;
    Address address;
    ContactDetail contactDetail;

    Set<Guardian> guardians;
    Guardian testGuardian;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        daughter = Student.builder().build();
        kids = new HashSet<>();
        kids.add(daughter);
        address = Address.builder().build();
        contactDetail = ContactDetail.builder().build();
        testGuardian = Guardian.builder().address(address).students(kids).contactDetail(contactDetail).firstName("Eric").id(1L).build();
        guardians = new HashSet<>();
        guardians.add(testGuardian);
        //provides each test method with a mock controller based on studentIndexController
        mockMvc = MockMvcBuilders.standaloneSetup(guardianController).build();
    }

    @Test
    void listGuardians() throws Exception {
        when(guardianService.findAll()).thenReturn(guardians);

        mockMvc.perform(get("/guardians"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("guardians/index"))
                .andExpect(model().attribute("guardians", hasSize(1)));
    }

    @Test
    void index() throws Exception {
        when(guardianService.findAll()).thenReturn(guardians);

        mockMvc.perform(get("/guardians/index"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("guardians/index"))
                .andExpect(model().attribute("guardians", hasSize(1)));
    }

    @Test
    void indexHTML() throws Exception {
        when(guardianService.findAll()).thenReturn(guardians);

        mockMvc.perform(get("/guardians/index.html"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("guardians/index"))
                .andExpect(model().attribute("guardians", hasSize(1)));
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
                .andExpect(model().attributeExists("newGuardian"));
    }
}