package com.srm.controllers;

import com.srm.exceptions.NotFoundException;
import com.srm.model.academic.Subject;
import com.srm.model.people.*;
import com.srm.services.peopleServices.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;

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
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String listTeachers(Model model, String lastName) {
        //model is handled by Spring

        if (lastName == null || lastName.isEmpty()) {
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

        //build new Teacher object with empty (non-null) properties
        if (teacher.getFirstName() == null || teacher.getLastName() == null) {
            model.addAttribute("teacher", Teacher.builder().build());
            model.addAttribute("selectedName", "");
            model.addAttribute("selectedMainSubject", "");
        } else {
            //proceed with the search
            log.info("Teacher search initiated");
            if (teacher.getFirstName().isEmpty() && teacher.getLastName().isEmpty()) {
                result.rejectValue("firstName", "notFound", "Enter at least one name");
            } else {
                List<Teacher> results = teacherService.findAllByFirstNameLikeAndLastNameLike(teacher.getFirstName(),
                        teacher.getLastName());
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
    public ModelAndView showTeacher(@PathVariable String teacherId) {
        ModelAndView mav = new ModelAndView("/teachers/teacherDetails");
        Teacher teacher = teacherService.findById(Long.valueOf(teacherId));

        //assume for now that there are only up to two guardians registered per student
        List<Subject> subjectsTaught = new ArrayList<>(teacher.getSubjects());

        if (!subjectsTaught.isEmpty()) {
            if (subjectsTaught.size() == 1) {
                mav.addObject("subject1Name", subjectsTaught.get(0).getSubjectName());
                mav.addObject("subject2Name", "(no second subject recorded)");
            } else if (subjectsTaught.size() == 2) {
                mav.addObject("subject1Name", subjectsTaught.get(0).getSubjectName());
                mav.addObject("subject2Name", subjectsTaught.get(1).getSubjectName());
            } else {
                mav.addObject("subject1Name", "(no record)");
                mav.addObject("subject2Name", "(no record)");
            }
        }

        mav.addObject("subjectsTaught", subjectsTaught);
        mav.addObject("teacher", teacher);
        return mav;
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        //avoid rendering null subjectSets in the future
        model.addAttribute("teacher", Teacher.builder().subjects(new HashSet<>()).build());
        return "/teachers/newTeacher";
    }

    // also note that teacher.id is effectively null at this point because the template is not allowed to set id
    @PostMapping("/new")
    public String processCreationForm(@Valid Teacher teacher) {
        if (teacher.getLastName().isBlank() || teacher.getFirstName().isBlank()) {
            //todo: impl form validation
            log.info("Enter both names");
            return "redirect:/teachers/new/";
        }
        // save() handles the id allocation (no further intervention needed for new saves)
        if (teacher.isNew()) {
            Teacher savedTeacher = teacherService.save(teacher);
            return "redirect:/teachers/" + savedTeacher.getId() + "/edit";
        } else {
            log.info("Current object already exists");
            return "/teachers/updateTeacher";
        }
    }

    @GetMapping("/{teacherId}/edit")
    public String initUpdateForm(@PathVariable String teacherId, Model model) {
        Teacher teacherFound = teacherService.findById(Long.valueOf(teacherId));

        // assume for now that there are only up to two subjects registered per teacher
        // and subject1 is always assigned before subject2

        List<Subject> subjectList = new ArrayList<>(teacherFound.getSubjects());

        Subject temp1 = Subject.builder().build();
        Subject temp2 = Subject.builder().build();

        if (teacherFound.getSubjects().isEmpty()) {
            model.addAttribute("subject1", temp1);
            model.addAttribute("subject2", temp2);
        } else if (teacherFound.getSubjects().size() == 1) {
            model.addAttribute("subject1", subjectList.get(0));
            model.addAttribute("subject2", temp2);
        } else if (teacherFound.getSubjects().size() == 2) {
            model.addAttribute("subject1", subjectList.get(0));
            model.addAttribute("subject2", subjectList.get(1));
        }
        model.addAttribute("teacher", teacherFound);
        return "/teachers/updateTeacher";
    }

    @PostMapping("/{teacherId}/edit")
    public String processUpdateOwnerForm(@Valid Teacher teacher, @PathVariable String teacherId) {
        //todo check for other identical records before saving

        //recall all other variables and pass to the DB
        Teacher teacherOnFile = teacherService.findById(Long.valueOf(teacherId));
        teacherOnFile.setFirstName(teacher.getFirstName());
        teacherOnFile.setLastName(teacher.getLastName());
        teacherOnFile.setDepartment(teacher.getDepartment());

        Teacher savedTeacher = teacherService.save(teacherOnFile);
        return "redirect:/teachers/" + savedTeacher.getId();
    }

    //note that the ResponseStatus annotation is repeated here since 'local' annotations take precedence, all other
    //class level annotations are ignored
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){

        log.error("Handling 'not found' exception");
        log.error(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormat(Exception exception){

        log.error("Handling 'number format' exception");
        log.error(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("400error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }
}
