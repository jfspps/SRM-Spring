package com.srm.controllers;

import com.srm.model.people.ContactDetail;
import com.srm.model.people.Guardian;
import com.srm.model.people.Student;
import com.srm.model.people.Teacher;
import com.srm.services.academicServices.SubjectService;
import com.srm.services.peopleServices.GuardianService;
import com.srm.services.peopleServices.StudentService;
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
import java.util.Set;

@Slf4j
//all routings below proceed /students, not the root (see indexController)
@RequestMapping({"/students"})
@Controller
public class StudentController {

    private final StudentService studentService;
    private final GuardianService guardianService;
    private final TeacherService teacherService;
    private final SubjectService subjectService;

    //constructor service injection; when StudentIndexController is instantiated, it is injected with a one-time StudentService
    public StudentController(StudentService studentService, GuardianService guardianService, TeacherService teacherService, SubjectService subjectService) {
        this.studentService = studentService;
        this.guardianService = guardianService;
        this.teacherService = teacherService;
        this.subjectService = subjectService;
    }

    //prevent the HTTP form POST from editing listed properties
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    //the following are automatically added to Model model in StudentController, identified in template by " "
    @ModelAttribute("guardians")
    public Set<Guardian> populateGuardians(Student student){
        return student.getGuardians();
    }

//    @ModelAttribute("teacher")
//    public Teacher populatePersonalTutor(Student student){
//        return student.getTeacher();
//    }

    @ModelAttribute("contactDetails")
    public ContactDetail populateContactDetails(Student student){
        return student.getContactDetail();
    }

    // routing ====================================================================================================

    @GetMapping({"", "/", "/index", "/index.html"})
    public String listStudents(Model model, String lastName) {
        //model is handled by Spring

        if(lastName == null || lastName.isEmpty()){
            //execute findAll() and assign Set to Thymeleaf "students" at corresponding index.html
            model.addAttribute("students", studentService.findAll());
        } else {
            //findByLastName returns one Student not a Set of Students, if found
            model.addAttribute("students", studentService.findAllByLastNameLike(lastName));
        }
        return "students/index";
    }

    //on GET request, <form> passes 'student' with properties set on form
    @GetMapping({"/search", "/search.html"})
    public String findStudents(Student student, BindingResult result, Model model) {

        //build new Student object with empty (non-null) properties
        if (student.getFirstName() == null || student.getLastName() == null) {
            model.addAttribute("student", Student.builder().build());
        } else {
            //proceed with the search
            if (student.getFirstName().isEmpty() && student.getLastName().isEmpty()){
                result.rejectValue("firstName", "notFound", "Enter at least one name");
            } else {
                List<Student> results = studentService.findAllByFirstNameLikeAndLastNameLike(student.getFirstName(),
                        student.getLastName());
                if (results.isEmpty()) {
                    // rejectValue(String field, String errorCode, String defaultMessage)
                    result.rejectValue("firstName", "notFound", "Not found");
                } else {
                    Set<Student> resultsAsSet = new HashSet<>(results);
                    model.addAttribute("studentsFound", resultsAsSet);
                }
            }
        }
        return "/students/search";
    }

    //get to a subject's details by ID
    @GetMapping("/{studentId}")
    public ModelAndView showStudent(@PathVariable String studentId) {
        ModelAndView mav = new ModelAndView("/students/studentDetails");
        Student student = studentService.findById(Long.valueOf(studentId));

        //assume for now that there are only up to two guardians registered per student
        List<Guardian> guardians = new ArrayList<>(student.getGuardians());
        String guardian1Name = "";
        String guardian2Name = "";

        //may require more general refactor in the future...
        if (guardians.size() == 1){
            guardian1Name = guardians.get(0).getFirstName() + " " + guardians.get(0).getLastName();
        }
        //if above fails then subsequent Set (and List) entries are not defined either, hence outOfBounds exception
        if (guardians.size() == 2){
            guardian1Name = guardians.get(0).getFirstName() + " " + guardians.get(0).getLastName();
            guardian2Name = guardians.get(1).getFirstName() + " " + guardians.get(1).getLastName();
        }
        mav.addObject("guardian1Name", guardian1Name);
        mav.addObject("guardian2Name", guardian2Name);
        mav.addObject("student", student);
        return mav;
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        model.addAttribute("student", Student.builder().build());
        return "/students/newStudent";
    }

    // also note that student.id is effectively null at this point because the template is not allowed to set id
    @PostMapping("/new")
    public String processCreationForm(@Valid Student student) {
        if (student.getLastName().isBlank() || student.getFirstName().isBlank()){
            //todo: impl form validation
            log.info("Enter both names");
            return "redirect:/students/new/";
        }
        // save() handles the id allocation (no further intervention needed for new saves)
        if (student.isNew()) {
            Student savedStudent = studentService.save(student);
            return "redirect:/students/" + savedStudent.getId() + "/edit";
        } else {
            log.info("Current object already exists");
            return "/students/updateStudent";
        }
    }

    @GetMapping("/{studentId}/edit")
    public String initUpdateForm(@PathVariable String studentId, Model model) {
        Student studentFound = studentService.findById(Long.valueOf(studentId));
        model.addAttribute("student", studentFound);
        return "/students/updateStudent";
    }

    @PostMapping("/{studentId}/edit")
    public String processUpdateOwnerForm(@Valid Student student, @PathVariable String studentId) {
        //todo check for other identical records before saving

        //recall all other variables and pass to the DB
        Student studentOnFile = studentService.findById(Long.valueOf(studentId));
        studentOnFile.setFirstName(student.getFirstName());
        studentOnFile.setLastName(student.getLastName());

        Student savedStudent = studentService.save(studentOnFile);
        return "redirect:/students/" + savedStudent.getId();
    }

    @GetMapping("/{studentId}/tutor/edit")
    public String initUpdateTutorForm(@PathVariable String studentId, Model model){
        Student student = studentService.findById(Long.valueOf(studentId));
        if (student.getTeacher() == null){
            model.addAttribute("teacher", Teacher.builder().build());
        } else {
            model.addAttribute("teacher", student.getTeacher());
        }
        return "/teachers/newTeacher";
    }

    @PostMapping("/{studentId}/tutor/edit")
    public String processUpdateTutorForm(@Valid Teacher teacher, @PathVariable String studentId){
        //todo form validation
        Student student = studentService.findById(Long.valueOf(studentId));
        //user must fill in all fields (query is case insensitive)
        Teacher found = teacherService.findByFirstNameAndLastNameAndDepartment(
                teacher.getFirstName(), teacher.getLastName(), teacher.getDepartment());
        if (found != null){
            student.setTeacher(found);
            //todo update formGroupList
        } else {
            //new teacher, save() handles id
            Teacher savedTeacher = teacherService.save(teacher);
            student.setTeacher(savedTeacher);
        }
        student.setId(Long.valueOf(studentId));
        Student savedStudent = studentService.save(student);
        return "redirect:/students/" + savedStudent.getId() + "/edit";
    }

    //update of a guardian's student (set) details
    @GetMapping("/guardian/{guardianId}/edit")
    public String initUpdateStudentSetForm(@PathVariable String guardianId, ModelMap model) {
        Guardian guardian = guardianService.findById(Long.valueOf(guardianId));
        List<Student> students = new ArrayList<>(guardian.getStudents());
        if (students.size() == 1){
            model.addAttribute("guardian", guardian)
                    .addAttribute("student1", students.get(0))
                    .addAttribute("student2", Student.builder().build());
        } else if (students.size() == 2) {
            model.addAttribute("guardian", guardian)
                    .addAttribute("student1", students.get(0))
                    .addAttribute("student2", students.get(1));
        } else {
            model.addAttribute("guardian", guardian)
                    .addAttribute("student1", Student.builder().build())
                    .addAttribute("student2", Student.builder().build());
        }
//        log.info(students.get(0).getFirstName() + ' ' + students.get(0).getLastName());
        return "/guardians/updateStudentSet";
    }

    @PostMapping("/guardian/{guardianId}/edit")
    public String processUpdateStudentSetForm(@PathVariable String guardianId, @Valid Student student1) {
        //this is a hack (see SubjectController and GuardianController for more background)
//        log.info(student1.getFirstName() + ' ' + student1.getLastName());

        //here is the hack to accommodate this for now:
        Guardian guardian = guardianService.findById(Long.valueOf(guardianId));
        List<Student> savedStudents = new ArrayList<>();
        String[] studentsFirstNames = student1.getFirstName().split(",");
        String[] studentsLastNames = student1.getLastName().split(",");

        //registers guardian with found students
        Set<Guardian> guardiansRegistered = new HashSet<>();
        guardiansRegistered.add(guardian);

        Student newStudentOne;
        Student newStudentTwo;

        if (!studentsFirstNames[0].isBlank() || !studentsFirstNames[0].equals("null")){
            Student foundOne = studentService.findByFirstAndLastName(studentsFirstNames[0], studentsLastNames[0]);
            if (foundOne == null){
                // not on the DB
                newStudentOne = Student.builder()
                        .firstName(studentsFirstNames[0])
                        .lastName(studentsLastNames[0])
                        .guardians(guardiansRegistered).build();

                savedStudents.add(studentService.save(newStudentOne));
            } else {
                foundOne.setGuardians(guardiansRegistered);
                savedStudents.add(foundOne);
            }
        }

        if (studentsFirstNames.length > 1){
            if (!studentsFirstNames[1].isBlank() || !studentsFirstNames[1].equals("null")){
                Student foundAnother = studentService.findByFirstAndLastName(studentsFirstNames[1], studentsLastNames[1]);
                if (foundAnother == null){
                    // not on the DB
                    newStudentTwo = Student.builder()
                            .firstName(studentsFirstNames[1])
                            .lastName(studentsLastNames[1])
                            .guardians(guardiansRegistered).build();
                    savedStudents.add(studentService.save(newStudentTwo));
                } else {
                    foundAnother.setGuardians(guardiansRegistered);
                    savedStudents.add(foundAnother);
                }
            }

            // from functional testing, the order that the guardians are listed seems to depend on the student ID???

//            Student temp = savedStudents.get(0);
//            Student temp2 = savedStudents.get(1);
//            savedStudents.clear();
//            savedStudents.add(temp2);
//            savedStudents.add(temp);
//            log.info("Saved students is now: " + savedStudents.toString());
        }

        guardian.setStudents(new HashSet<>(savedStudents));
        guardian.setId(Long.valueOf(guardianId));
        Guardian savedGuardian = guardianService.save(guardian);

        return "redirect:/guardians/" + savedGuardian.getId() + "/edit";
    }
}
