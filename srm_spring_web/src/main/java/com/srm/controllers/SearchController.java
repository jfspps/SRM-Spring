package com.srm.controllers;

import com.srm.model.people.Student;
import com.srm.services.peopleServices.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
public class SearchController {

    private final StudentService studentService;

    public SearchController(StudentService studentService) {
        this.studentService = studentService;
    }

    //on GET request, inject Student POJO as a Model to access Student properties
    @GetMapping({"/search", "/search.html"})
    public String index(Student student, BindingResult result, Model model) {

        //build new Student object with empty (non-null) properties
        if (student.getFirstName() == null || student.getLastName() == null) {
            model.addAttribute("student", Student.builder().build());
            log.info("New student search started");
        } else {
            //proceed with the search
            List<Student> results = studentService.findAllByFirstNameLikeAndLastNameLike(student.getFirstName(), student.getLastName());
            log.info("Found: " + results.size());
        }
        return null;

        //        if (results.isEmpty()) {
//            // no owners found
//            result.rejectValue("lastName", "notFound", "not found");
//            return "search";
//        } else if (results.size() == 1) {
//            // 1 owner found
//            student = results.get(0);
//            return "redirect:/students/" + student.getId();
//        } else {
//            // multiple owners found
//            model.addAttribute("students", results);
//            return "students/index";
//        }
    }
}
