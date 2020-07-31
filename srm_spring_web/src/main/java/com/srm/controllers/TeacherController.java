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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            model.addAttribute("teachers", teacherService.findAllByLastNameLike(lastName));
        }
        return "teachers/index";
    }

    //on GET request, inject Teacher POJO as a Model to access Student properties
    @GetMapping({"/search", "/search.html"})
    public String index(Teacher teacher, BindingResult result, Model model) {

        List<Teacher> results = new ArrayList<>();

        //build new Teacher object with empty (non-null) properties
        if (teacher.getFirstName() == null || teacher.getLastName() == null) {
            model.addAttribute("teaceher", Teacher.builder().build());
        } else {
            //proceed with the search
            log.info("Teacher search initiated");
            if (teacher.getFirstName().isEmpty() && teacher.getLastName().isEmpty()){
                result.rejectValue("firstName", "notFound", "Enter at least one name");
            } else {
                results = teacherService.findAllByFirstNameLikeAndLastNameLike(teacher.getFirstName(), teacher.getLastName());
                if (results.isEmpty()) {
                    // rejectValue(String field, String errorCode, String defaultMessage)
                    result.rejectValue("firstName", "notFound", "Not found");
                } else {
                    Set<Teacher> resultsAsSet = new HashSet<>(results);
                    model.addAttribute("teachersFound", resultsAsSet);
                }
            }
        }
        return "/teachers/search";
    }
}
