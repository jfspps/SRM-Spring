package com.srm.controllers;

import com.srm.model.services.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping({"/students"})
@Controller
public class StudentIndexController {

    private final StudentService studentService;

    //constructor service injection; when StudentIndexController is instantiated, it is injected with a one-time StudentService
    public StudentIndexController(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String listStudents(Model model) {
        //model is handled by Spring

        //inject model into controller object with attribute 'students'
        model.addAttribute("students", studentService.findAll());
        return "students/index";
    }
}
