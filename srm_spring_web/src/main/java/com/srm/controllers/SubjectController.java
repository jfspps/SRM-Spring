package com.srm.controllers;

import com.srm.model.academic.Subject;
import com.srm.model.people.Teacher;
import com.srm.services.academicServices.SubjectService;
import com.srm.services.peopleServices.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;

//all routings below proceed /subjects, not the root (see indexController)
@Slf4j
@RequestMapping({"/subjects"})
@Controller
public class SubjectController {

    private final SubjectService subjectService;
    private final TeacherService teacherService;

    public SubjectController(SubjectService subjectService, TeacherService teacherService) {
        this.subjectService = subjectService;
        this.teacherService = teacherService;
    }

    //prevent the HTTP form POST from editing listed properties
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String listSubjects(Model model, String subjectName) {
        //model is handled by Spring

        if (subjectName == null || subjectName.isEmpty()) {
            //execute findAll() and assign Set to Thymeleaf "subjects" at corresponding index.html
            model.addAttribute("subjects", subjectService.findAll());
        } else {
            model.addAttribute("subjects", subjectService.findBySubjectName(subjectName));
        }
        return "/subjects/index";
    }

    //get to a subject's details by ID
    @GetMapping("/{subjectId}")
    public ModelAndView showSubject(@PathVariable Long subjectId) {
        ModelAndView mav = new ModelAndView("/subjects/subjectDetails");
        mav.addObject("subject", subjectService.findById(subjectId));
        return mav;
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        //avoid passing null composite objects (teachers)
        model.addAttribute("newSubject", Subject.builder().teachers(new HashSet<>()).build());
        return "/subjects/newSubject";
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid Subject subject) {
        if (subject.isNew()) {
            subjectService.save(subject);
            return "redirect:/subjects/index";
        } else {
            log.info("Current object already exists");
            return "/subjects/updateSubject";
        }
    }

    @GetMapping("/{subjectId}/edit")
    public String initUpdateSubjectForm(@PathVariable Long subjectId, Model model) {
        model.addAttribute("updateSubject", subjectService.findById(subjectId));
        return "/subjects/updateSubject";
    }

    @PostMapping("/{subjectId}/edit")
    public String processUpdateSubjectForm(@Valid Subject subject, @PathVariable Long subjectId) {
        //ensures a new object is not created
        subject.setId(subjectId);
        subjectService.save(subject);
        return "redirect:/subjects/index";
    }
}