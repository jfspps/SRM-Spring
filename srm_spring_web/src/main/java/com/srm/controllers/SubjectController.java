package com.srm.controllers;

import com.srm.model.academic.Subject;
import com.srm.model.people.Teacher;
import com.srm.services.academicServices.SubjectService;
import com.srm.services.peopleServices.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
    public ModelAndView showSubject(@PathVariable String subjectId) {
        ModelAndView mav = new ModelAndView("/subjects/subjectDetails");
        mav.addObject("subject", subjectService.findById(Long.valueOf(subjectId)));
        return mav;
    }

    @GetMapping("/new")
    public String initSubjectCreationForm(Model model) {
        //avoid passing null composite objects (teachers)
        model.addAttribute("newSubject", Subject.builder().teachers(new HashSet<>()).build());
        return "/subjects/newSubject";
    }

    //newSubject template model is bound to 'subject', which is handled in this method;
    //BindResult and @Valid work to validate form data and return error messages to the user
    //it seems BindingResult must immediately follow the bound (model) object...
    @PostMapping("/new")
    public String processSubjectCreationForm(@Valid @ModelAttribute("newSubject") Subject subject, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
//                log.info(objectError.toString());
            });
            return "/subjects/newSubject";
        }

        if (subject.isNew()) {
            subjectService.save(subject);
            return "redirect:/subjects/index";
        } else {
            log.info("Current object already exists");
            return "/subjects/updateSubject";
        }
    }

    @GetMapping("/{subjectId}/edit")
    public String initUpdateSubjectForm(@PathVariable String subjectId, Model model) {
        model.addAttribute("updateSubject", subjectService.findById(Long.valueOf(subjectId)));
        return "/subjects/updateSubject";
    }

    @PostMapping("/{subjectId}/edit")
    public String processUpdateSubjectForm(Subject subject, @PathVariable String subjectId) {
        //currently not permitted to change subject names (user must enter a new Subject)
        //leave this for possible future use

//        Subject subjectOnFile = subjectService.findById(Long.valueOf(subjectId));
//        subjectOnFile.setSubjectName(subject.getSubjectName());
//        subjectService.save(subjectOnFile);
        return "redirect:/subjects/index";
    }

    @GetMapping("/teacher/{teacherId}/edit")
    public String initUpdateSubjectSetForm(@PathVariable String teacherId, ModelMap model) {
        Teacher teacher = teacherService.findById(Long.valueOf(teacherId));
        List<Subject> subjects = new ArrayList<>(teacher.getSubjects());
        if (subjects.size() == 1){
            model.addAttribute("teacher", teacher)
                    .addAttribute("subject1", subjects.get(0))
                    .addAttribute("subject2", Subject.builder().build());
        } else if (subjects.size() == 2){
            model.addAttribute("teacher", teacher)
                    .addAttribute("subject1", subjects.get(0))
                    .addAttribute("subject2", subjects.get(1));
        } else {
            model.addAttribute("teacher", teacher)
                    .addAttribute("subject1", Subject.builder().build())
                    .addAttribute("subject2", Subject.builder().build());
        }
        return "/subjects/updateSubjectSet";
    }

    @PostMapping("/teacher/{teacherId}/edit")
    public String processUpdateSubjectSetForm(@PathVariable String teacherId, @Valid Subject subject1) {
        //this is a hack: it seems subject1 is actually a comma-separated List<Subject> (getSubjectName uses default toString()?)
//        log.info(subject1.getSubjectName());

        //here is the hack to accommodate this for now:
        Teacher teacher = teacherService.findById(Long.valueOf(teacherId));
        List<Subject> savedSubjects = new ArrayList<>();
        String[] subjects = subject1.getSubjectName().split(",");

        if (!subjects[0].isBlank() || !subjects[0].equals("null")){
            Subject foundOne = subjectService.findBySubjectName(subjects[0]);
            if (foundOne == null){
                //subjectOne is not on the DB
                foundOne = subjectService.save(Subject.builder().subjectName(subjects[0]).build());
            }
            savedSubjects.add(foundOne);
        }

        if (subjects.length > 1){
            if (!subjects[1].isBlank() || !subjects[1].equals("null")){
                Subject foundAnother = subjectService.findBySubjectName(subjects[1]);
                if (foundAnother == null){
                    // not on the DB
                    foundAnother = subjectService.save(Subject.builder().subjectName(subjects[1]).build());
                }
                savedSubjects.add(foundAnother);
            }
            log.info("Saved subjects is currently: " + savedSubjects.toString());
            // from functional testing, we need to reverse the subject order in savedSubjects if unique IDs are not
            // set for the <input> tags of updateSubjectSet.html (currently no need at present)

//            Subject temp = savedSubjects.get(0);
//            Subject temp2 = savedSubjects.get(1);
//            savedSubjects.clear();
//            savedSubjects.add(temp2);
//            savedSubjects.add(temp);
//            log.info("Saved subjects is now: " + savedSubjects.toString());
        }

        teacher.setSubjects(new HashSet<>(savedSubjects));
        teacher.setId(Long.valueOf(teacherId));
        Teacher savedTeacher = teacherService.save(teacher);

        return "redirect:/teachers/" + savedTeacher.getId() + "/edit";
    }
}