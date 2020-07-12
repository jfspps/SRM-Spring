package com.srm.controllers;

import com.srm.model.services.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

//all routings below proceed /subjects, not the root (see indexController)
@RequestMapping({"/subjects"})
@Controller
public class SubjectIndexController {

    private final SubjectService subjectService;

    public SubjectIndexController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String listSubjects(Model model) {
        //model is handled by Spring

        //inject model into controller object with attribute 'subjects'
        model.addAttribute("subjects", subjectService.findAll());
        return "subjects/index";
    }
}
