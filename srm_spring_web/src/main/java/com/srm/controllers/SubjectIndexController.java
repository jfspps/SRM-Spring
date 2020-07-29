package com.srm.controllers;

import com.srm.services.academicServices.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

//all routings below proceed /subjects, not the root (see indexController)
@RequestMapping({"/subjects"})
@Controller
public class SubjectIndexController {

    private final SubjectService subjectService;

    public SubjectIndexController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //prevent the HTTP form POST from editing listed properties
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String listSubjects(Model model) {
        //model is handled by Spring

        //execute findAll() and assign Set to Thymeleaf "subjects" at corresponding index.html
        model.addAttribute("subjects", subjectService.findAll());
        return "subjects/index";
    }
}
