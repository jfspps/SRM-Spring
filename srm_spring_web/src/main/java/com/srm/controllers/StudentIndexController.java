package com.srm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StudentIndexController {

    @RequestMapping({"/students", "/students/index", "/students/index.html"})
    public String listStudents() {
        return "students/index";
    }
}
