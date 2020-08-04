package com.srm.controllers;

import com.srm.model.people.*;
import com.srm.services.academicServices.SubjectService;
import com.srm.services.peopleServices.GuardianService;
import com.srm.services.peopleServices.StudentService;
import com.srm.services.peopleServices.TeacherService;
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

@Slf4j
//all routings below proceed /students, not the root (see indexController)
@RequestMapping({"/students"})
@Controller
public class StudentController {

    private final StudentService studentService;
    private final GuardianService guardianService;
    private final TeacherService teacherService;
    private final SubjectService subjectService;

    //constructor service injection; when StudentIndexController is instantiated, it is injected with a one-time StudentService
    public StudentController(StudentService studentService, GuardianService guardianService, TeacherService teacherService, SubjectService subjectService) {
        this.studentService = studentService;
        this.guardianService = guardianService;
        this.teacherService = teacherService;
        this.subjectService = subjectService;
    }

    //prevent the HTTP form POST from editing listed properties
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    //the following are automatically added to Model model in StudentController, identified in template by " "
    @ModelAttribute("guardians")
    public Set<Guardian> populateGuardians(Student student){
        return student.getGuardians();
    }

    @ModelAttribute("teacher")
    public Teacher populatePersonalTutor(Student student){
        return student.getTeacher();
    }

    @ModelAttribute("contactDetails")
    public ContactDetail populateContactDetails(Student student){
        return student.getContactDetail();
    }

    // routing ====================================================================================================

    @GetMapping({"", "/", "/index", "/index.html"})
    public String listStudents(Model model, String lastName) {
        //model is handled by Spring

        if(lastName == null || lastName.isEmpty()){
            //execute findAll() and assign Set to Thymeleaf "students" at corresponding index.html
            model.addAttribute("students", studentService.findAll());
        } else {
            //findByLastName returns one Student not a Set of Students, if found
            model.addAttribute("students", studentService.findAllByLastNameLike(lastName));
        }
        return "students/index";
    }

    //on GET request, <form> passes 'student' with properties set on form
    @GetMapping({"/search", "/search.html"})
    public String findStudents(Student student, BindingResult result, Model model) {

        //build new Student object with empty (non-null) properties
        if (student.getFirstName() == null || student.getLastName() == null) {
            model.addAttribute("student", Student.builder().build());
        } else {
            //proceed with the search
            if (student.getFirstName().isEmpty() && student.getLastName().isEmpty()){
                result.rejectValue("firstName", "notFound", "Enter at least one name");
            } else {
                List<Student> results = studentService.findAllByFirstNameLikeAndLastNameLike(student.getFirstName(),
                        student.getLastName());
                if (results.isEmpty()) {
                    // rejectValue(String field, String errorCode, String defaultMessage)
                    result.rejectValue("firstName", "notFound", "Not found");
                } else {
                    Set<Student> resultsAsSet = new HashSet<>(results);
                    model.addAttribute("studentsFound", resultsAsSet);
                }
            }
        }
        return "/students/search";
    }

    //get to a subject's details by ID
    @GetMapping("/{studentId}")
    public ModelAndView showStudent(@PathVariable Long studentId) {
        ModelAndView mav = new ModelAndView("/students/studentDetails");
        Student student = studentService.findById(studentId);

        //assume for now that there are only up to two guardians registered per student
        List<Guardian> guardians = new ArrayList<>(student.getGuardians());
        String guardian1Name = "";
        String guardian2Name = "";

        //may require more general refactor in the future...
        if (guardians.size() == 1){
            guardian1Name = guardians.get(0).getFirstName() + " " + guardians.get(0).getLastName();
        }
        //if above fails then subsequent Set (and List) entries are not defined either, hence outOfBounds exception
        if (guardians.size() == 2){
            guardian2Name = guardians.get(1).getFirstName() + " " + guardians.get(1).getLastName();
        }
        mav.addObject("guardian1Name", guardian1Name);
        mav.addObject("guardian2Name", guardian2Name);
        mav.addObject("student", student);
        return mav;
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        model.addAttribute("newStudent", Student.builder().build());
        model.addAttribute("tutorFound", 0);
        model.addAttribute("contactsFound", 0);
        return "/students/newUpdateStudent";
    }

    // also note that student.id is effectively null at this point because the template is not allowed to set id
    @PostMapping("/new")
    public String processCreationForm(@Valid Student student) {
        if (student.getLastName().isBlank() || student.getFirstName().isBlank()){
            //todo: impl form validation
            log.info("Enter both names");
            return "redirect:/students/new/";
        }
        // save() handles the id allocation (no further intervention needed for new saves)
        if (student.isNew()) {
            Student savedStudent = studentService.save(student);
            return "redirect:/students/" + savedStudent.getId();
        } else {
            log.info("Current object already exists");
            return "/students/newUpdateStudent";
        }
    }

    @GetMapping("/{studentId}/edit")
    public String initUpdateForm(@PathVariable Long studentId, Model model) {
        Student studentFound = studentService.findById(studentId);
        if (studentFound.getTeacher() == null) {
            model.addAttribute("tutorFound", 0);
        } else
            model.addAttribute("tutorFound", 1);

        if (studentFound.getContactDetail() == null) {
            model.addAttribute("contactsFound", 0);
        } else
            model.addAttribute("contactsFound", 1);

        model.addAttribute("student", studentFound);
        return "/students/newUpdateStudent";
    }

    @PostMapping("/{studentId}/edit")
    public String processUpdateOwnerForm(@Valid Student student, @PathVariable Long studentId) {
        student.setId(studentId);
        Student savedStudent = studentService.save(student);
        return "redirect:/students/" + savedStudent.getId();
    }
}
