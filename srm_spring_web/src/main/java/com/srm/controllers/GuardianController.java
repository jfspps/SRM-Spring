package com.srm.controllers;

import com.srm.model.people.Address;
import com.srm.model.people.Guardian;
import com.srm.model.people.Student;
import com.srm.services.peopleServices.GuardianService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//all routings below proceed /guardians, not the root (see indexController)
@Slf4j
@RequestMapping({"/guardians"})
@Controller
public class GuardianController {

    private final GuardianService guardianService;

    //constructor service injection; when GuardianIndexController is instantiated, it is injected with a one-time GuardianService
    public GuardianController(GuardianService guardianService) {
        this.guardianService = guardianService;
    }

    //prevent the HTTP form POST from editing listed properties
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String listGuardians(Model model, String lastName) {
        //model is handled by Spring

        if (lastName == null || lastName.isEmpty()){
            //execute findAll() and assign Set to Thymeleaf "guardians" at corresponding index.html
            model.addAttribute("guardians", guardianService.findAll());
        } else {
            model.addAttribute("guardians", guardianService.findAllByLastNameLike(lastName));
        }
        return "guardians/index";
    }

    //on GET request, inject Guardian POJO as a Model to access Student properties
    @GetMapping({"/search", "/search.html"})
    public String index(Guardian guardian, BindingResult result, Model model) {

        List<Guardian> results = new ArrayList<>();

        //build new Guardian object with empty (non-null) properties
        if (guardian.getFirstName() == null || guardian.getLastName() == null) {
            model.addAttribute("guardian", Guardian.builder().build());
            model.addAttribute("selectedName", "");
            model.addAttribute("selectedStudentName", "");
            model.addAttribute("selectedAddress", "");
            model.addAttribute("selectedMobile", "");
            model.addAttribute("selectedEmail", "");
        } else {
            //proceed with the search
            log.info("Guardian search initiated");
            if (guardian.getFirstName().isEmpty() && guardian.getLastName().isEmpty()){
                result.rejectValue("firstName", "notFound", "Enter at least one name");
            } else {
                results = guardianService.findAllByFirstNameLikeAndLastNameLike(guardian.getFirstName(), guardian.getLastName());
                if (results.isEmpty()) {
                    // rejectValue(String field, String errorCode, String defaultMessage)
                    result.rejectValue("firstName", "notFound", "Not found");
                } else {
                    Set<Guardian> resultsAsSet = new HashSet<>(results);
                    Guardian first = results.get(0);
                    model.addAttribute("guardiansFound", resultsAsSet);
                    model.addAttribute("selectedName", first.getFirstName() + " " + first.getLastName());
                    StringBuilder studentNames = new StringBuilder();
                    for (Student student: first.getStudents()) {
                        studentNames.append(student.getFirstName()).append(" ").append(student.getLastName()).append(";");
                    }
                    model.addAttribute("selectedStudentName", studentNames);
                    StringBuilder addressString = new StringBuilder();
                    Address address = first.getAddress();
                    if (address != null){
                        addressString.append(address.getFirstLine()).append(", ").append(address.getSecondLine())
                                .append(", ").append(address.getPostcode());
                        model.addAttribute("selectedAddress", addressString);
                    }
                    if (first.getContactDetail() != null){
                        model.addAttribute("selectedMobile", first.getContactDetail().getPhoneNumber());
                        model.addAttribute("selectedEmail", first.getContactDetail().getEmail());
                    }
                }
            }
        }
        return "/guardians/search";
    }
}
