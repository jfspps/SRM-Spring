package com.srm.controllers;

import com.srm.model.academic.Subject;
import com.srm.model.people.Guardian;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
            model.addAttribute("teacher", Teacher.builder().build());
            model.addAttribute("selectedName", "");
            model.addAttribute("selectedMainSubject", "");
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
                    Teacher first = results.get(0);
                    model.addAttribute("teachersFound", resultsAsSet);
                    model.addAttribute("selectedName", first.getFirstName() + " " + first.getLastName());

                    if (first.getSubjects().stream().findFirst().isPresent())
                        model.addAttribute("selectedMainSubject", first.getSubjects().stream().findFirst().get().getSubjectName());
                }
            }
        }
        return "/teachers/search";
    }

    //get to a subject's details by ID
    @GetMapping("/{teacherId}")
    public ModelAndView showTeacher(@PathVariable Long teacherId) {
        ModelAndView mav = new ModelAndView("/teachers/teacherDetails");
        Teacher teacher = teacherService.findById(teacherId);
        mav.addObject("teacher", teacher);
        //assume for now that there are only up to two guardians registered per student
        List<Subject> subjects = new ArrayList<>(teacher.getSubjects());
        String subject1Name = "";
        String subject2Name = "";

        //may require more general refactor in the future...
        if (subjects.size() == 1){
            subject1Name = subjects.get(0).getSubjectName();
        }
        //if above fails then subsequent Set (and List) entries are not defined either, hence outOfBounds exception
        if (subjects.size() == 2){
            subject2Name = subjects.get(1).getSubjectName();
        }

        mav.addObject("subject1Name", subject1Name);
        mav.addObject("subject2Name", subject2Name);
        return mav;
    }
}
