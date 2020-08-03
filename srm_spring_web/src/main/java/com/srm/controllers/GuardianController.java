package com.srm.controllers;

import com.srm.model.people.Address;
import com.srm.model.people.ContactDetail;
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

import javax.naming.Binding;
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

    //constructor service injection; when GuardianIndexController is instantiated, it is injected with a one-time GuardianService
    public GuardianController(GuardianService guardianService, StudentService studentService, AddressService addressService, ContactDetailService contactDetailService) {
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
            if (guardian.getFirstName().isEmpty() && guardian.getLastName().isEmpty()) {
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
                    for (Student student : first.getStudents()) {
                        studentNames.append(student.getFirstName()).append(" ").append(student.getLastName()).append(";");
                    }
                    model.addAttribute("selectedStudentName", studentNames);
                    StringBuilder addressString = new StringBuilder();
                    Address address = first.getAddress();
                    if (address != null) {
                        addressString.append(address.getFirstLine()).append(", ").append(address.getSecondLine())
                                .append(", ").append(address.getPostcode());
                        model.addAttribute("selectedAddress", addressString);
                    }
                    if (first.getContactDetail() != null) {
                        model.addAttribute("selectedMobile", first.getContactDetail().getPhoneNumber());
                        model.addAttribute("selectedEmail", first.getContactDetail().getEmail());
                    }
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

        if (guardian.getAddress() == null) {
            guardian.setAddress(Address.builder().firstLine("").secondLine("").postcode("").build());
        }
        if (guardian.getContactDetail() == null) {
            guardian.setContactDetail(ContactDetail.builder().email("").phoneNumber("").build());
        }
        mav.addObject("student1Name", student1Name);
        mav.addObject("student2Name", student2Name);
        mav.addObject("guardian", guardian);
        return mav;
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        //todo associate the composites with guardian before rendering newGuardian e.g. PetController (Owner is a paramter)

        /**
         * Pet pet = new Pet();
         *         owner.getPets().add(pet);
         *         pet.setOwner(owner);
         *         model.addAttribute("pet", pet);*/

        model.addAttribute("guardian", Guardian.builder().build());
        model.addAttribute("addressFound", 0);
        model.addAttribute("contactsFound", 0);
        return "/guardians/newUpdateGuardian";
    }

    // also note that guardian.id is effectively null at this point because the template is not allowed to set id
    @PostMapping("/new")
    public String processCreationForm(@Valid Guardian guardian) {
        if (guardian.getLastName().isBlank() || guardian.getFirstName().isBlank()){
            //todo: impl form validation
            log.info("Enter both names");
            return "redirect:/guardians/new/";
        }
            // save() handles the id allocation (no further intervention needed for new saves)
            if (guardian.isNew()) {
                Guardian savedGuardian = guardianService.save(guardian);
                return "redirect:/guardians/" + savedGuardian.getId();
            } else {
                log.info("Current object already exists");
                return "/guardians/updateGuardian";
            }
    }

    @GetMapping("/{guardianId}/edit")
    public String initUpdateForm(@PathVariable Long guardianId, Model model) {
        Guardian guardianFound = guardianService.findById(guardianId);
        if (guardianFound.getAddress() == null) {
            model.addAttribute("addressFound", 0);
        } else
            model.addAttribute("addressFound", 1);

        if (guardianFound.getContactDetail() == null) {
            model.addAttribute("contactsFound", 0);
        } else
            model.addAttribute("contactsFound", 1);

        model.addAttribute("guardian", guardianFound);
        return "/guardians/newUpdateGuardian";
    }

    @PostMapping("/{guardianId}/edit")
    public String processUpdateOwnerForm(@Valid Guardian guardian, @PathVariable Long guardianId) {
        guardian.setId(guardianId);
        Guardian savedGuardian = guardianService.save(guardian);
        return "redirect:/guardians/" + savedGuardian.getId();
    }
}
