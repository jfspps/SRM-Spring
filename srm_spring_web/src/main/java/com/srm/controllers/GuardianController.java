package com.srm.controllers;

import com.srm.model.academic.Subject;
import com.srm.model.people.Address;
import com.srm.model.people.Guardian;
import com.srm.model.people.Student;
import com.srm.model.people.Teacher;
import com.srm.services.peopleServices.AddressService;
import com.srm.services.peopleServices.ContactDetailService;
import com.srm.services.peopleServices.GuardianService;
import com.srm.services.peopleServices.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
                student1Name = students.get(0).getFirstName() + " " + students.get(0).getLastName();
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
        //todo check for other identical records before saving

        //recall all other variables and pass to the DB
        Guardian guardianOnFile = guardianService.findById(guardianId);
        guardianOnFile.setFirstName(guardian.getFirstName());
        guardianOnFile.setLastName(guardian.getLastName());

        Guardian savedGuardian = guardianService.save(guardianOnFile);
        return "redirect:/guardians/" + savedGuardian.getId();
    }


    // update of a student's guardian details
    @GetMapping("/student/{studentId}/edit")
    public String initUpdateGuardianSetForm(@PathVariable Long studentId, ModelMap model) {
        Student student = studentService.findById(studentId);
        List<Guardian> guardians = new ArrayList<>(student.getGuardians());
        if (guardians.size() == 1) {
            model.addAttribute("student", student)
                    .addAttribute("guardian1", guardians.get(0))
                    .addAttribute("guardian2", Guardian.builder().build());
        } else if (guardians.size() == 2) {
            model.addAttribute("student", student)
                    .addAttribute("guardian1", guardians.get(0))
                    .addAttribute("guardian2", guardians.get(1));
        } else {
            model.addAttribute("student", student)
                    .addAttribute("guardian1", Guardian.builder().build())
                    .addAttribute("guardian2", Guardian.builder().build());
        }
//        log.info(guardians.get(0).getFirstName() + ' ' + guardians.get(0).getLastName());
        return "/students/updateGuardianSet";
    }

    @PostMapping("/student/{studentId}/edit")
    public String processUpdateGuardianSetForm(@PathVariable Long studentId, @Valid Guardian guardian1) {
        //this is a hack (see SubjectController for more background)
        // one can show that passing @Valid Guardian guardian2 as a parameter and logging as below results in an
        // identical List structure, so for now I implement guardian1 as the sole list
//        log.info(guardian1.getFirstName() + ' ' + guardian1.getLastName());

        //here is the hack to accommodate this for now:
        Student student = studentService.findById(studentId);
        List<Guardian> savedGuardians = new ArrayList<>();
        String[] guardiansFirstNames = guardian1.getFirstName().split(",");
        String[] guardiansLastNames = guardian1.getLastName().split(",");

        //registers student with found guardians
        Set<Student> studentsRegistered = new HashSet<>();
        studentsRegistered.add(student);

        Guardian newGuardianOne;
        Guardian newGuardianTwo;

        if (!guardiansFirstNames[0].isBlank() || !guardiansFirstNames[0].equals("null")){
            Guardian foundOne = guardianService.findByFirstAndLastName(guardiansFirstNames[0], guardiansLastNames[0]);
            if (foundOne == null){
                // not on the DB
                newGuardianOne = Guardian.builder()
                        .firstName(guardiansFirstNames[0])
                        .lastName(guardiansLastNames[0])
                        .students(studentsRegistered).build();

                savedGuardians.add(guardianService.save(newGuardianOne));
            } else {
                foundOne.setStudents(studentsRegistered);
                savedGuardians.add(foundOne);
            }
        }

        if (guardiansFirstNames.length > 1){
            if (!guardiansFirstNames[1].isBlank() || !guardiansFirstNames[1].equals("null")){
                Guardian foundAnother = guardianService.findByFirstAndLastName(guardiansFirstNames[1], guardiansLastNames[1]);
                if (foundAnother == null){
                    // not on the DB
                    newGuardianTwo = Guardian.builder()
                            .firstName(guardiansFirstNames[1])
                            .lastName(guardiansLastNames[1])
                            .students(studentsRegistered).build();
                    savedGuardians.add(guardianService.save(newGuardianTwo));
                } else {
                    foundAnother.setStudents(studentsRegistered);
                    savedGuardians.add(foundAnother);
                }
            }

            // from functional testing, the order that the guardians are listed seems to depend on the student ID???

//            Guardian temp = savedGuardians.get(0);
//            Guardian temp2 = savedGuardians.get(1);
//            savedGuardians.clear();
//            savedGuardians.add(temp2);
//            savedGuardians.add(temp);
//            log.info("Saved guardians is now: " + savedGuardians.toString());
        }

        student.setGuardians(new HashSet<>(savedGuardians));
        student.setId(studentId);
        Student savedStudent = studentService.save(student);

        return "redirect:/students/" + savedStudent.getId() + "/edit";
    }
}
