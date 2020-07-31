package com.srm.controllers;

import com.srm.model.people.Student;
import com.srm.model.people.Teacher;
import com.srm.services.peopleServices.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

//all routings below proceed /teachers, not the root (see indexController)
@RequestMapping({"/teachers"})
@Slf4j
@Controller
public class TeacherController {

    private final TeacherService teacherService;

    //constructor service injection; when TeacherIndexController is instantiated, it is injected with a one-time TeacherService
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    //prevent the HTTP form POST from editing listed properties
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String listTeachers(Model model, String lastName) {
        //model is handled by Spring

        if(lastName == null || lastName.isEmpty()){
            //execute findAll() and assign Set to Thymeleaf "teachers" at corresponding index.html
            model.addAttribute("teachers", teacherService.findAll());
        } else {
            model.addAttribute("teachers", teacherService.findByLastName(lastName));
        }
        return "teachers/index";
    }

    //on GET request, inject Student POJO as a Model to access Student properties
    @GetMapping({"/search", "/search.html"})
    public String index(Teacher teacher, BindingResult result, Model model) {

        List<Teacher> results = new ArrayList<>();

        //build new Student object with empty (non-null) properties
        if (teacher.getFirstName() == null || teacher.getLastName() == null) {
            model.addAttribute("teacher", Teacher.builder().build());
            log.info("New teacher search started");
        } else {
            //proceed with the search
            results = teacherService.findAllByFirstNameLikeAndLastNameLike(teacher.getFirstName(), teacher.getLastName());
        }
        if (results.isEmpty()) {
            // no teachers found
            // rejectValue(String field, String errorCode, String defaultMessage)
            result.rejectValue("firstName", "notFound", "not found");
            return "/teachers/search";
        } else if (results.size() == 1) {
            // 1 owner found
            teacher = results.get(0);
            log.info("Found one teacher on record");
            return null;
//            return "redirect:/teachers/" + teacher.getId();
        } else {
            // multiple owners found
            model.addAttribute("teachers", results);
            log.info("Found " + results.size() + " teachers on record");
            return null;
//            return "teachers/index";
        }
    }
}
