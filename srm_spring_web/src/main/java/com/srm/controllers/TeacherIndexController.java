package com.srm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TeacherIndexController {

    @RequestMapping({"/teachers", "/teachers/index", "/teachers/index.html"})
    public String listGuardians() {
        return "teachers/index";
    }
}
