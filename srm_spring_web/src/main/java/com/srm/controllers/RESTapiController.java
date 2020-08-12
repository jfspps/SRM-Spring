package com.srm.controllers;

import com.srm.model.academic.Subject;
import com.srm.model.people.Guardian;
import com.srm.model.people.Student;
import com.srm.model.people.Teacher;
import com.srm.services.academicServices.SubjectService;
import com.srm.services.peopleServices.GuardianService;
import com.srm.services.peopleServices.StudentService;
import com.srm.services.peopleServices.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//example (REST style) API based controller to return JSONs
@RestController
@Slf4j
@RequestMapping("/api")
public class RESTapiController {

    private final StudentService studentService;
    private final GuardianService guardianService;
    private final TeacherService teacherService;
    private final SubjectService subjectService;

    public RESTapiController(StudentService studentService, GuardianService guardianService, TeacherService teacherService, SubjectService subjectService) {
        this.studentService = studentService;
        this.guardianService = guardianService;
        this.teacherService = teacherService;
        this.subjectService = subjectService;
    }

    //@ResponseBody would place the output directly to <body> but this annotation is handled along with @Controller using
    //@RestController (useful if all mappings are REST based)

    //Many-to-Many and Many-to-One relationships leading to infinitely long JSONs
    //read about @JsonIgnore
    //(https://stackoverflow.com/questions/20813496/tomcat-exception-cannot-call-senderror-after-the-response-has-been-committed)

    @GetMapping("/students")
    public List<Student> returnStudentsJSON(){
        log.info(String.valueOf(studentService.findAll().size()));
        return new ArrayList<>(studentService.findAll());
    }

    @GetMapping("/student/{studentId}")
    public Student returnStudentByIdJSON(@PathVariable String studentId){

        if (studentService.findById(Long.valueOf(studentId)) == null){
            return Student.builder().build();
        } else
            return studentService.findById(Long.valueOf(studentId));
    }

    @GetMapping("/teachers")
    public Set<Teacher> returnTeachersJSON(){

        return teacherService.findAll();
    }

    @GetMapping("/teacher/{teacherId}")
    public Teacher returnTeacherByIdJSON(@PathVariable String teacherId){

        if (teacherService.findById(Long.valueOf(teacherId)) == null){
            return Teacher.builder().build();
        } else
            return teacherService.findById(Long.valueOf(teacherId));
    }

    @GetMapping("/guardians")
    public Set<Guardian> returnGuardiansJSON(){

        return guardianService.findAll();
    }

    @GetMapping("/guardian/{guardianId}")
    public Guardian returnGuardianByIdJSON(@PathVariable String guardianId){

        if (guardianService.findById(Long.valueOf(guardianId)) == null){
            return Guardian.builder().build();
        } else
            return guardianService.findById(Long.valueOf(guardianId));
    }

    @GetMapping("/subjects")
    public Set<Subject> returnSubjectsJSON(){

        return subjectService.findAll();
    }

    @GetMapping("/subject/{subjectId}")
    public Subject returnSubjectByIdJSON(@PathVariable String subjectId){

        if (subjectService.findById(Long.valueOf(subjectId)) == null){
            return Subject.builder().build();
        } else
            return subjectService.findById(Long.valueOf(subjectId));
    }
}
