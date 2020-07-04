package com.srm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping({"/guardians"})
@Controller
public class GuardianIndexController {

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String listGuardians() {
        return "guardians/index";
    }
}
