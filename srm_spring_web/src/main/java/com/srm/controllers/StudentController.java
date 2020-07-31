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
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

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
            model.addAttribute("students", studentService.findByLastName(lastName));
        }
        return "students/index";
    }

    //on GET request, inject Student POJO as a Model to access Student properties
    @GetMapping({"/search", "/search.html"})
    public String index(Student student, BindingResult result, Model model) {

        List<Student> results = new ArrayList<>();

        //build new Student object with empty (non-null) properties
        if (student.getFirstName() == null || student.getLastName() == null) {
            model.addAttribute("student", Student.builder().build());
            log.info("New student search started");
        } else {
            //proceed with the search
            results = studentService.findAllByFirstNameLikeAndLastNameLike(student.getFirstName(), student.getLastName());
        }
        if (results.isEmpty()) {
            // no students found
            // rejectValue(String field, String errorCode, String defaultMessage)
            result.rejectValue("firstName", "notFound", "not found");
            return "/students/search";
        } else if (results.size() == 1) {
            // 1 owner found
            student = results.get(0);
            log.info("Found one student on record");
            return null;
//            return "redirect:/students/" + student.getId();
        } else {
            // multiple owners found
            model.addAttribute("students", results);
            log.info("Found " + results.size() + " students on record");
            return null;
//            return "students/index";
        }
    }

}
