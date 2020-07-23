package com.srm.controllers;

import com.srm.services.peopleServices.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//all routings below proceed /students, not the root (see indexController)
@RequestMapping({"/students"})
@Controller
public class StudentIndexController {

    private final StudentService studentService;

    //constructor service injection; when StudentIndexController is instantiated, it is injected with a one-time StudentService
    public StudentIndexController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String listStudents(Model model) {
        //model is handled by Spring

        //execute findAll() and assign Set to Thymeleaf "students" at corresponding index.html
        model.addAttribute("students", studentService.findAll());
        return "students/index";
    }
}
