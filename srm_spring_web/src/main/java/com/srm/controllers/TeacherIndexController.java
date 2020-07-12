package com.srm.controllers;

import com.srm.services.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

//all routings below proceed /teachers, not the root (see indexController)
@RequestMapping({"/teachers"})
@Controller
public class TeacherIndexController {

    private final TeacherService teacherService;

    //constructor service injection; when TeacherIndexController is instantiated, it is injected with a one-time TeacherService
    public TeacherIndexController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String listGuardians(Model model) {
        //model is handled by Spring

        //inject model into controller object with attribute 'teachers'
        model.addAttribute("teachers", teacherService.findAll());
        return "teachers/index";
    }
}
