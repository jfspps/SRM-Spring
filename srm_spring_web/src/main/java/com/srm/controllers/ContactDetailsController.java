package com.srm.controllers;

import com.srm.model.people.ContactDetail;
import com.srm.model.people.Guardian;
import com.srm.model.people.Student;
import com.srm.model.people.Teacher;
import com.srm.services.peopleServices.GuardianService;
import com.srm.services.peopleServices.StudentService;
import com.srm.services.peopleServices.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Guard;

@Slf4j
@Controller
public class ContactDetailsController {

    private final GuardianService guardianService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    public ContactDetailsController(GuardianService guardianService, StudentService studentService,
                                    TeacherService teacherService) {
        this.guardianService = guardianService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    //prevent the HTTP form POST from editing listed properties
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    // guardian contact details
    @GetMapping("/guardians/{guardianId}/contacts/new")
    public String initCreationFormGuardian(@PathVariable Long guardianId, Model model) {
        ContactDetail contactDetail = ContactDetail.builder().build();
        Guardian guardian = guardianService.findById(guardianId);
        guardian.setContactDetail(contactDetail);
        //Guardian to ContactDetail is one to one (no DB relationship from the converse)
        model.addAttribute("guardian", guardian);
        model.addAttribute("contact", contactDetail);
        return "/contacts/newUpdateGuardianContact";
    }

    // student contact details
    @GetMapping("/students/{studentId}/contacts/new")
    public String initCreationFormStudent(@PathVariable Long studentId, Model model) {
        ContactDetail contactDetail = ContactDetail.builder().build();
        Student student = studentService.findById(studentId);
        student.setContactDetail(contactDetail);

        model.addAttribute("student", student);
        model.addAttribute("contact", contactDetail);
        return "/contacts/newUpdateStudentContact";
    }

    // teacher contact details
    @GetMapping("/teachers/{teacherId}/contacts/new")
    public String initCreationFormTeacher(@PathVariable Long teacherId, Model model) {
        ContactDetail contactDetail = ContactDetail.builder().build();
        Teacher teacher = teacherService.findById(teacherId);
        teacher.setContactDetail(contactDetail);

        model.addAttribute("teacher", teacher);
        model.addAttribute("contact", contactDetail);
        return "/contacts/newUpdateTeacherContact";
    }


}
