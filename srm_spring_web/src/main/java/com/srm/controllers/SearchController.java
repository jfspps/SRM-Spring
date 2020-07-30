package com.srm.controllers;

import com.srm.model.people.Student;
import com.srm.services.peopleServices.GuardianService;
import com.srm.services.peopleServices.StudentService;
import com.srm.services.peopleServices.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class SearchController {

    private final StudentService studentService;
    private final GuardianService guardianService;
    private final TeacherService teacherService;

    public SearchController(StudentService studentService, GuardianService guardianService, TeacherService teacherService) {
        this.studentService = studentService;
        this.guardianService = guardianService;
        this.teacherService = teacherService;
    }

    //on GET request, inject Student POJO as a Model to access Student properties
    @GetMapping({"/search", "/search.html"})
    public String index(Student student, BindingResult result, Model model) {

        List<Student> results = new ArrayList<>();

        //build new Student object with empty (non-null) properties
        if (student.getFirstName() == null || student.getLastName() == null) {
            model.addAttribute("student", Student.builder().build());
            log.info("New student search started");
        } else {
            //proceed with the search
            results = studentService.findAllByFirstNameLikeAndLastNameLike(student.getFirstName(), student.getLastName());
        }
        if (results.isEmpty()) {
            // no students found
            // rejectValue(String field, String errorCode, String defaultMessage)
            result.rejectValue("firstName", "notFound", "not found");
            return "search";
        } else if (results.size() == 1) {
            // 1 owner found
            student = results.get(0);
            log.info("Found one student on record");
            return null;
//            return "redirect:/students/" + student.getId();
        } else {
            // multiple owners found
            model.addAttribute("students", results);
            log.info("Found " + results.size() + " students on record");
            return null;
//            return "students/index";
        }
    }
}
