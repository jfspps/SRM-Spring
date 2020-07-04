package com.srm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping({"/teachers"})
@Controller
public class TeacherIndexController {

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String listGuardians() {
        return "teachers/index";
    }
}
