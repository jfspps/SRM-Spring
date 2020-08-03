package com.srm.controllers;

import com.srm.model.academic.Subject;
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

        List<Student> results;

        //build new Student object with empty (non-null) properties
        if (student.getFirstName() == null || student.getLastName() == null) {
            model.addAttribute("student", Student.builder().build());
        } else {
            //proceed with the search
            log.info("Student search initiated");
            if (student.getFirstName().isEmpty() && student.getLastName().isEmpty()){
                result.rejectValue("firstName", "notFound", "Enter at least one name");
            } else {
                results = studentService.findAllByFirstNameLikeAndLastNameLike(student.getFirstName(), student.getLastName());
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
        mav.addObject("student", student);
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

        String noOfSubjectsStudied;
        if (student.getSubjectClassLists() != null){
            noOfSubjectsStudied = String.valueOf(student.getSubjectClassLists().size());
        } else
            noOfSubjectsStudied = "0";

        mav.addObject("guardian1Name", guardian1Name);
        mav.addObject("guardian2Name", guardian2Name);
        mav.addObject("noOfSubjectsStudied", noOfSubjectsStudied);
        return mav;
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        //avoid passing null composite objects (contactDetail, formGroup, guardians, personal tutor and subject list)
        model.addAttribute("newStudent",
                Student.builder().firstName("").lastName("")
                .contactDetail(ContactDetail.builder().build())
                .formGroupList(FormGroupList.builder().build())
                .guardians(new HashSet<>())
                .personalTutor(Teacher.builder().build())
                .subjectClassLists(new HashSet<>())
                .build());
        return "/students/newStudent";
    }
}
