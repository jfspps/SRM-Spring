package com.srm.controllers;

import com.srm.model.people.Address;
import com.srm.model.people.Guardian;
import com.srm.services.peopleServices.AddressService;
import com.srm.services.peopleServices.GuardianService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @Mock
    AddressService addressService;

    @Mock
    GuardianService guardianService;

    @InjectMocks
    AddressController addressController;

    MockMvc mockMvc;
    Address address;
    Guardian guardian;

    @BeforeEach
    void setUp() {
        address = Address.builder().build();
        guardian = Guardian.builder().build();
        //provides each test method with a mock controller based on studentIndexController
        mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();
    }

    @Test
    void initCreationForm() throws Exception{
        //Guardian with ID 1L requests a new address
        // @ModelAttribute calls findById()
        when(guardianService.findById(anyLong())).thenReturn(guardian);

        mockMvc.perform(get("/guardians/1/address/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("/addresses/newUpdateAddress"))
                .andExpect(model().attributeExists("address"))
                .andExpect(model().attributeExists("guardian"));    //guardian is @ModelAttribute
    }

    @Test
    void processCreationForm() throws Exception{
        // @ModelAttribute calls findById(); 2L is needed for routing
        when(guardianService.findById(anyLong())).thenReturn(Guardian.builder().id(2L).build());

        mockMvc.perform(post("/guardians/2/address/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/guardians/2/edit"))
                .andExpect(model().attributeExists("guardian"));

        verify(addressService).save(any());
    }

    @Test
    void initUpdateForm() throws Exception {
        // @ModelAttribute calls findById()
        when(guardianService.findById(anyLong())).thenReturn(Guardian.builder().build());

        mockMvc.perform(get("/guardians/2/address/3/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("/addresses/newUpdateAddress"))
                .andExpect(model().attributeExists("guardian"));
    }

    @Test
    void processUpdateForm() throws Exception {
        // @ModelAttribute calls findById()
        when(guardianService.findById(anyLong())).thenReturn(Guardian.builder().id(2L).build());

        mockMvc.perform(post("/guardians/2/address/3/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/guardians/2/edit"))
                .andExpect(model().attributeExists("guardian"));

        verify(addressService).save(any());
    }
}