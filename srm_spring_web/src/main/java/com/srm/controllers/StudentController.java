package com.srm.controllers;

import com.srm.model.people.Student;
import com.srm.services.peopleServices.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

    //constructor service injection; when StudentIndexController is instantiated, it is injected with a one-time StudentService
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //prevent the HTTP form POST from editing listed properties
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

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
            model.addAttribute("selectedName", "");
            model.addAttribute("selectedPersonalTutorName", "");
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
                    Student first = results.get(0);
                    model.addAttribute("studentsFound", resultsAsSet);
                    model.addAttribute("selectedName", first.getFirstName() + " " + first.getLastName());
                    model.addAttribute("selectedPersonalTutorName", first.getTeacher().getFirstName() + " " + first.getTeacher().getLastName());
                }
            }
        }
        return "/students/search";
    }

    //get to a subject's details by ID
    @GetMapping("/{studentId}")
    public ModelAndView showSubject(@PathVariable Long studentId) {
        ModelAndView mav = new ModelAndView("/students/studentDetails");
        mav.addObject("student", studentService.findById(studentId));
        return mav;
    }
}
