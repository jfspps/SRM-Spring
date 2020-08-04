package com.srm.controllers;

import com.srm.model.people.Address;
import com.srm.model.people.Guardian;
import com.srm.services.peopleServices.AddressService;
import com.srm.services.peopleServices.GuardianService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping({"/guardians/{guardianId}"})
@Controller
public class AddressController {

    private final AddressService addressService;
    private final GuardianService guardianService;

    public AddressController(AddressService addressService, GuardianService guardianService) {
        this.addressService = addressService;
        this.guardianService = guardianService;
    }

    //prevent the HTTP form POST from editing listed properties
    @InitBinder("guardian")
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("guardian")
    public Guardian findGuardian(@PathVariable Long guardianId) {
        return guardianService.findById(guardianId);
    }

    @GetMapping("/address/new")
    public String initCreationForm(Guardian guardian, Model model) {
        Address address = Address.builder().build();
        guardian.setAddress(address);
        //Guardian to Address is many to one (no DB relationship from Address to Guardian)
        model.addAttribute("address", address);
        return "/addresses/newUpdateAddress";
    }

    @PostMapping("/address/new")
    public String processCreationForm(@Valid Address address, Guardian guardian) {
        //todo impl form validation
        guardian.setAddress(address);
        //Guardian to Address is many to one (no DB relationship from Address to Guardian)
        addressService.save(address);
        return "redirect:/guardians/" + guardian.getId() + "/edit";
    }

    @GetMapping("/address/{addressId}/edit")
    public String initUpdateForm(@PathVariable Long addressId, Model model) {

        //Guardian to Address is many to one (no DB relationship from Address to Guardian)
        model.addAttribute("address", addressService.findById(addressId));
        return "/addresses/newUpdateAddress";
    }

    @PostMapping("/address/{addressId}/edit")
    public String processUpdateForm(@Valid Address address, Guardian guardian, @PathVariable Long addressId) {
        //todo impl form validation
        guardian.setAddress(address);
        address.setId(addressId);
        //Guardian to Address is many to one (no DB relationship from Address to Guardian)
        addressService.save(address);
        return "redirect:/guardians/" + guardian.getId() + "/edit";
    }
}
