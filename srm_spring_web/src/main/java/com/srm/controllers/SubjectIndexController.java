package com.srm.controllers;

import com.srm.services.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//all routings below proceed /subjects, not the root (see indexController)
@RequestMapping({"/subjects"})
@Controller
public class SubjectIndexController {

    private final SubjectService subjectService;

    public SubjectIndexController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String listSubjects(Model model) {
        //model is handled by Spring

        //execute findAll() and assign Set to Thymeleaf "subjects" at corresponding index.html
        model.addAttribute("subjects", subjectService.findAll());
        return "subjects/index";
    }
}
