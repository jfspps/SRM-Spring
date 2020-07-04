package com.srm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping({"/students"})
@Controller
public class StudentIndexController {

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String listStudents() {
        return "students/index";
    }
}
