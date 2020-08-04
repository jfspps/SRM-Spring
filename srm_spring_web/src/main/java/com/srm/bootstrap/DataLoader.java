package com.srm.bootstrap;

import com.srm.model.academic.Subject;
import com.srm.model.people.*;
import com.srm.services.academicServices.SubjectService;
import com.srm.services.peopleServices.GuardianService;
import com.srm.services.peopleServices.StudentService;
import com.srm.services.peopleServices.TeacherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

//set as a Spring Bean with @Component, and run run()
@Component
public class DataLoader implements CommandLineRunner {

    //this block and the constructor below ensure that the services persist
    private final GuardianService guardianService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final SubjectService subjectService;

    //implement dependency inversion (@Autowired annotation not required) offered since each service is annotated
    public DataLoader(GuardianService guardianService, StudentService studentService, TeacherService teacherService,
                      SubjectService subjectService) {
        this.guardianService = guardianService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.subjectService = subjectService;
    }

    @Override
    public void run(String... args) throws Exception {
        //check if there is anything on the DB before reloading (teacherService seems a good candidate...)

        if (teacherService.findAll().size() == 0) {
            loadData();
        }
    }

    private void loadData() {
        //build a temporary POJO from Student, Teacher and Guardian classes and add (inject) to each respective service

        //note below, some POJOs employ Lombok's Builder Pattern

        Student student1 = new Student();
        student1.setFirstName("John");
        student1.setLastName("Smith");
        Student student2 = Student.builder().firstName("Elizabeth").lastName("Jones").build();
        Student student3 = Student.builder().firstName("Helen").lastName("Jones").build();

        ContactDetail teacher1Contact = ContactDetail.builder().email("teacher1@school.com").phoneNumber("9847324").build();
        ContactDetail teacher2Contact = ContactDetail.builder().email("teacher2@school.com").phoneNumber("4023307").build();
        Teacher teacher1 = Teacher.builder().firstName("Keith").lastName("Thomson").contactDetail(teacher1Contact).build();
        student1.setTeacher(teacher1);

        Teacher teacher2 = new Teacher();
        teacher2.setFirstName("Julie");
        teacher2.setLastName("Adams");
        teacher2.setContactDetail(teacher2Contact);
        student2.setTeacher(teacher2);
        student3.setTeacher(teacher2);

        Address address1 = Address.builder().firstLine("88 Penine Way").secondLine("Farnborough").postcode("CHG9475JF").build();
        ContactDetail contactDetail1 = ContactDetail.builder().phoneNumber("3479324732").email("guardian1@email.com").build();
        Guardian guardian1 = Guardian.builder().firstName("Alan").lastName("Smith").address(address1).build();
        guardian1.setContactDetail(contactDetail1);
        student1.setContactDetail(contactDetail1);
        Set<Guardian> student1Guardians = new HashSet<>();
        student1Guardians.add(guardian1);
        student1.setGuardians(student1Guardians);
        Set<Student> guardian1Students = new HashSet<>();
        guardian1Students.add(student1);
        guardian1.setStudents(guardian1Students);

        Guardian guardian2 = new Guardian();
        guardian2.setFirstName("Ruth");
        guardian2.setLastName("Jones");
        guardian2.setContactDetail(ContactDetail.builder().phoneNumber("02374320427").email("guardian2@email.com").build());
        guardian2.setAddress(Address.builder().firstLine("7B Gossfer Drive").secondLine("Racoon City").postcode("ZJGKF97657DD").build());

        Set<Guardian> student2Guardians = new HashSet<>();
        student2Guardians.add(guardian2);
        Set<Student> guardian2Students = new HashSet<>();
        guardian2Students.add(student2);
        guardian2.setStudents(guardian2Students);

        student2.setGuardians(student2Guardians);
        student2.setContactDetail(ContactDetail.builder().phoneNumber("02374320427").email("guardian2@email.com").build());
        student3.setGuardians(student2Guardians);
        student3.setContactDetail(ContactDetail.builder().phoneNumber("02374320427").email("guardian2@email.com").build());

        guardianService.save(guardian1);
        guardianService.save(guardian2);
        System.out.println("Guardians loaded to DB...");

        Set<Student> studentgroup1 = new HashSet<>();
        studentgroup1.add(student1);
        Set<Student> studentgroup2 = new HashSet<>();
        studentgroup1.add(student2);
        studentgroup2.add(student3);
        FormGroupList formGroupList1 = FormGroupList.builder().studentList(studentgroup1).groupName("Group 1").teacher(teacher1).build();
        FormGroupList formGroupList2 = FormGroupList.builder().studentList(studentgroup2).groupName("Group 2").teacher(teacher2).build();
        student1.setFormGroupList(formGroupList1);
        student2.setFormGroupList(formGroupList2);
        student3.setFormGroupList(formGroupList2);

        studentService.save(student1);
        studentService.save(student2);
        studentService.save(student3);
        System.out.println("Students loaded to DB...");

        Subject subject1 = new Subject();
        subject1.setSubjectName("Math");
        Set<Subject> teacher1subjects = new HashSet<>();
        teacher1subjects.add(subject1);
        teacher1.setSubjects(teacher1subjects);
        teacher1.setDepartment("Mathematics");

        Subject subject2 = new Subject();
        subject2.setSubjectName("English");

        Set<Subject> teacher2subjects = new HashSet<>();
        teacher2subjects.add(subject2);
        teacher2.setSubjects(teacher2subjects);
        teacher2.setDepartment("English Lit and Lang");

        subjectService.save(subject1);
        subjectService.save(subject2);
        System.out.println("Subjects loaded to DB...");

        teacherService.save(teacher1);
        teacherService.save(teacher2);
        System.out.println("Teachers loaded to DB...");

        System.out.println("Finished uploading to DB");
    }
}
