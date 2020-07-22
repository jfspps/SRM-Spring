package com.srm.controllers;

import com.srm.services.GuardianService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//all routings below proceed /guardians, not the root (see indexController)
@RequestMapping({"/guardians"})
@Controller
public class GuardianIndexController {

    private final GuardianService guardianService;

    //constructor service injection; when GuardianIndexController is instantiated, it is injected with a one-time GuardianService
    public GuardianIndexController(GuardianService guardianService) {
        this.guardianService = guardianService;
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String listGuardians(Model model) {
        //model is handled by Spring

        //execute findAll() and assign Set to Thymeleaf "guardians" at corresponding index.html
        model.addAttribute("guardians", guardianService.findAll());
        return "guardians/index";
    }
}
