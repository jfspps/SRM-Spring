package com.srm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GuardianIndexController {

    @RequestMapping({"/guardians", "/guardians/index", "/guardians/index.html"})
    public String listGuardians() {
        return "guardians/index";
    }
}
