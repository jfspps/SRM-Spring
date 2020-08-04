package com.srm.controllers;

import com.srm.model.people.Address;
import com.srm.model.people.Guardian;
import com.srm.model.people.Student;
import com.srm.services.peopleServices.AddressService;
import com.srm.services.peopleServices.ContactDetailService;
import com.srm.services.peopleServices.GuardianService;
import com.srm.services.peopleServices.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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
    private final StudentService studentService;
    private final AddressService addressService;
    private final ContactDetailService contactDetailService;

    //constructor service injection; when GuardianController is instantiated, it is injected with a one-time services
    public GuardianController(GuardianService guardianService, StudentService studentService,
                              AddressService addressService, ContactDetailService contactDetailService) {
        this.guardianService = guardianService;
        this.studentService = studentService;
        this.addressService = addressService;
        this.contactDetailService = contactDetailService;
    }

    //prevent the HTTP form POST from editing listed properties
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String listGuardians(Model model, String lastName) {
        //model is handled by Spring

        if (lastName == null || lastName.isEmpty()) {
            //execute findAll() and assign Set to Thymeleaf "guardians" at corresponding index.html
            model.addAttribute("guardians", guardianService.findAll());
        } else {
            model.addAttribute("guardians", guardianService.findAllByLastNameLike(lastName));
        }
        return "guardians/index";
    }

    //on GET request, inject Guardian POJO as a Model to access Student properties
    @GetMapping({"/search", "/search.html"})
    public String searchGuardians(Guardian guardian, BindingResult result, Model model) {

        if (guardian.getFirstName() == null || guardian.getLastName() == null) {
            //run on first entry
            model.addAttribute("guardian", Guardian.builder().build());
        } else {
            //proceed with the search
            if (guardian.getFirstName().isEmpty() && guardian.getLastName().isEmpty()) {
                result.rejectValue("firstName", "notFound", "Enter at least one name");
            } else {
                List<Guardian> results = guardianService.findAllByFirstNameLikeAndLastNameLike(guardian.getFirstName(),
                        guardian.getLastName());
                if (results.isEmpty()) {
                    // rejectValue(String field, String errorCode, String defaultMessage)
                    result.rejectValue("firstName", "notFound", "Not found");
                } else {
                    Set<Guardian> resultsAsSet = new HashSet<>(results);
                    model.addAttribute("guardiansFound", resultsAsSet);
                }
            }
        }
        return "/guardians/search";
    }

    //get to a subject's details by ID
    @GetMapping("/{guardianId}")
    public ModelAndView showGuardian(@PathVariable Long guardianId) {
        ModelAndView mav = new ModelAndView("/guardians/guardianDetails");
        Guardian guardian = guardianService.findById(guardianId);

        //assume for now that there are only up to two students registered per guardian
        List<Student> students = new ArrayList<>(guardian.getStudents());
        String student1Name = "";
        String student2Name = "";
        if (guardian.getStudents() != null) {
            //may require more general refactor in the future...
            if (students.size() == 1) {
                student1Name = students.get(0).getFirstName() + " " + students.get(0).getLastName();
            }
            //if above fails then subsequent Set (and List) entries are not defined either, hence outOfBounds exception
            if (students.size() == 2) {
                student2Name = students.get(1).getFirstName() + " " + students.get(1).getLastName();
            }
        }

        mav.addObject("student1Name", student1Name);
        mav.addObject("student2Name", student2Name);
        mav.addObject("guardian", guardian);
        return mav;
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        model.addAttribute("guardian", Guardian.builder().build());
        return "/guardians/newGuardian";
    }

    // also note that guardian.id is effectively null at this point because the template is not allowed to set id
    @PostMapping("/new")
    public String processCreationForm(@Valid Guardian guardian) {
        if (guardian.getLastName().isBlank() || guardian.getFirstName().isBlank()) {
            //todo: impl form validation
            log.info("Enter both names");
            return "redirect:/guardians/new/";
        }
        // save() handles the id allocation (no further intervention needed for new saves)
        if (guardian.isNew()) {
            Guardian savedGuardian = guardianService.save(guardian);
            return "redirect:/guardians/" + savedGuardian.getId() + "/edit";
        } else {
            log.info("Current object already exists");
            return "/guardians/updateGuardian";
        }
    }

    @GetMapping("/{guardianId}/edit")
    public String initUpdateForm(@PathVariable Long guardianId, Model model) {
        Guardian guardianFound = guardianService.findById(guardianId);
        model.addAttribute("guardian", guardianFound);
        return "/guardians/updateGuardian";
    }

    @PostMapping("/{guardianId}/edit")
    public String processUpdateOwnerForm(@Valid Guardian guardian, @PathVariable Long guardianId) {
        guardian.setId(guardianId);
        Guardian savedGuardian = guardianService.save(guardian);
        return "redirect:/guardians/" + savedGuardian.getId();
    }
}
