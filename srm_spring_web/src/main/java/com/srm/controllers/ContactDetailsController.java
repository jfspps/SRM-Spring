package com.srm.controllers;

import com.srm.model.people.*;
import com.srm.services.peopleServices.ContactDetailService;
import com.srm.services.peopleServices.GuardianService;
import com.srm.services.peopleServices.StudentService;
import com.srm.services.peopleServices.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
public class ContactDetailsController {

    private final GuardianService guardianService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final ContactDetailService contactDetailService;

    public ContactDetailsController(GuardianService guardianService, StudentService studentService,
                                    TeacherService teacherService, ContactDetailService contactDetailService) {
        this.guardianService = guardianService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.contactDetailService = contactDetailService;
    }

    //prevent the HTTP form POST from editing listed properties
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    // guardian contact details ========================================================================
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

    @PostMapping("/guardians/{guardianId}/contacts/new")
    public String processCreationFormGuardian(@PathVariable Long guardianId, @Valid ContactDetail contactDetail) {
        //todo impl form validation
        Guardian guardian = guardianService.findById(guardianId);
        guardian.setContactDetail(contactDetail);

        contactDetailService.save(contactDetail);
        return "redirect:/guardians/" + guardianId + "/edit";
    }

    @GetMapping("/guardians/{guardianId}/contacts/{contactId}/edit")
    public String initUpdateFormGuardian(@PathVariable Long guardianId, @PathVariable Long contactId, Model model) {

        ContactDetail contactDetail = contactDetailService.findById(contactId);
        Guardian guardian = guardianService.findById(guardianId);

        model.addAttribute("guardian", guardian);
        model.addAttribute("contact", contactDetail);
        return "/contacts/newUpdateGuardianContact";
    }

    @PostMapping("/guardians/{guardianId}/contacts/{contactId}/edit")
    public String processUpdateFormGuardian(@PathVariable Long guardianId, @Valid ContactDetail contact) {
        //todo impl form validation

        Guardian guardian = guardianService.findById(guardianId);
        guardian.setContactDetail(contact);

        contact.setId(contact.getId());
        contactDetailService.save(contact);

        return "redirect:/guardians/" + guardianId + "/edit";
    }

    // student contact details ========================================================================
    @GetMapping("/students/{studentId}/contacts/new")
    public String initCreationFormStudent(@PathVariable Long studentId, Model model) {
        ContactDetail contactDetail = ContactDetail.builder().build();
        Student student = studentService.findById(studentId);
        student.setContactDetail(contactDetail);

        model.addAttribute("student", student);
        model.addAttribute("contact", contactDetail);
        return "/contacts/newUpdateStudentContact";
    }

    @PostMapping("/students/{studentId}/contacts/new")
    public String processCreationFormStudent(@PathVariable Long studentId, @Valid ContactDetail contactDetail) {
        //todo impl form validation
        Student student = studentService.findById(studentId);
        student.setContactDetail(contactDetail);

        contactDetailService.save(contactDetail);
        return "redirect:/students/" + studentId + "/edit";
    }

    @GetMapping("/students/{studentId}/contacts/{contactId}/edit")
    public String initUpdateFormStudent(@PathVariable Long studentId, @PathVariable Long contactId, Model model) {

        ContactDetail contactDetail = contactDetailService.findById(contactId);
        Student student = studentService.findById(studentId);

        model.addAttribute("student", student);
        model.addAttribute("contact", contactDetail);
        return "/contacts/newUpdateStudentContact";
    }

    @PostMapping("students/{studentId}/contacts/{contactId}/edit")
    public String processUpdateFormStudent(@Valid ContactDetail contact, @PathVariable Long studentId) {
        //todo impl form validation
        Student student = studentService.findById(studentId);
        student.setContactDetail(contact);

        contact.setId(contact.getId());
        contactDetailService.save(contact);

        return "redirect:/students/" + studentId + "/edit";
    }

    // teacher contact details ========================================================================
    @GetMapping("/teachers/{teacherId}/contacts/new")
    public String initCreationFormTeacher(@PathVariable Long teacherId, Model model) {
        ContactDetail contactDetail = ContactDetail.builder().build();
        Teacher teacher = teacherService.findById(teacherId);
        teacher.setContactDetail(contactDetail);

        model.addAttribute("teacher", teacher);
        model.addAttribute("contact", contactDetail);
        return "/contacts/newUpdateTeacherContact";
    }

    @PostMapping("/teachers/{teacherId}/contacts/new")
    public String processCreationFormTeacher(@PathVariable Long teacherId, @Valid ContactDetail contactDetail) {
        //todo impl form validation
        Teacher teacher = teacherService.findById(teacherId);
        teacher.setContactDetail(contactDetail);

        contactDetailService.save(contactDetail);
        return "redirect:/teachers/" + teacherId + "/edit";
    }

    @GetMapping("/teachers/{teacherId}/contacts/{contactId}/edit")
    public String initUpdateFormTeacher(@PathVariable Long teacherId, @PathVariable Long contactId, Model model) {

        ContactDetail contactDetail = contactDetailService.findById(contactId);
        Teacher teacher = teacherService.findById(teacherId);

        model.addAttribute("teacher", teacher);
        model.addAttribute("contact", contactDetail);
        return "/contacts/newUpdateTeacherContact";
    }

    @PostMapping("/teachers/{teacherId}/contacts/{contactId}/edit")
    public String processUpdateFormTeacher(@PathVariable Long teacherId, @Valid ContactDetail contact) {
        //todo impl form validation

        Teacher teacher = teacherService.findById(teacherId);
        teacher.setContactDetail(contact);

        contact.setId(contact.getId());
        contactDetailService.save(contact);

        return "redirect:/teachers/" + teacherId + "/edit";
    }
}
