package com.srm.controllers;

import com.srm.model.services.GuardianService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String listGuardians(Model model) {
        //model is handled by Spring

        //inject model into controller object with attribute 'guardians'
        model.addAttribute("guardians", guardianService.findAll());
        return "guardians/index";
    }
}
