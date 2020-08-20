package com.srm.bootstrap;

import com.srm.model.academic.Subject;
import com.srm.model.people.*;
import com.srm.services.academicServices.SubjectService;
import com.srm.services.peopleServices.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

//set as a Spring Bean with @Component, and run run()
@Slf4j
@Profile("h2")
@Component
public class DataLoader_h2 implements CommandLineRunner {

    //this block and the constructor below ensure that the services persist
    private final GuardianService guardianService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final SubjectService subjectService;
    private final AddressService addressService;
    private final ContactDetailService contactDetailService;
    private final FormGroupListService formGroupListService;

    //implement dependency inversion (@Autowired annotation not required) offered since each service is annotated
    public DataLoader_h2(GuardianService guardianService, StudentService studentService, TeacherService teacherService,
                         SubjectService subjectService, AddressService addressService, ContactDetailService contactDetailService, FormGroupListService formGroupListService) {
        this.guardianService = guardianService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.subjectService = subjectService;
        this.addressService = addressService;
        this.contactDetailService = contactDetailService;
        this.formGroupListService = formGroupListService;
    }

    @Override
    public void run(String... args) throws Exception {
        // this is always initialised since data is non-persistent under h2
            loadData();
            log.debug("Loaded SRM bootstrap data as h2");
    }

    private void loadData() {
        //build a temporary POJO from Student, Teacher and Guardian classes and add (inject) to each respective service
        //three students, two teachers and two guardians

        //contact details
        ContactDetail teacher1Contact = ContactDetail.builder().email("teacher1@school.com").phoneNumber("9847324").build();
        ContactDetail teacher2Contact = ContactDetail.builder().email("teacher2@school.com").phoneNumber("4023307").build();
        contactDetailService.save(teacher1Contact);
        contactDetailService.save(teacher2Contact);

        ContactDetail guardianContactDetail1 = ContactDetail.builder().phoneNumber("3479324732").email("guardian1@email.com").build();
        contactDetailService.save(guardianContactDetail1);
        ContactDetail guardianContactDetail2 = ContactDetail.builder().phoneNumber("02374320427").email("guardian2@email.com").build();
        contactDetailService.save(guardianContactDetail2);

        //addresses
        Address address1 = Address.builder().firstLine("88 Penine Way").secondLine("Farnborough").postcode("CHG9475JF").build();
        addressService.save(address1);
        Address address2 = Address.builder().firstLine("7B Gossfer Drive").secondLine("Racoon City").postcode("ZJGKF97657DD").build();
        addressService.save(address2);

        Student student1 = Student.builder().firstName("John").lastName("Smith").build();
        Student student2 = Student.builder().firstName("Elizabeth").lastName("Jones").build();
        Student student3 = Student.builder().firstName("Helen").lastName("Jones").build();

        Teacher teacher1 = Teacher.builder().firstName("Keith").lastName("Thomson").contactDetail(teacher1Contact).build();
        Teacher teacher2 = Teacher.builder().firstName("Julie").lastName("Adams").contactDetail(teacher2Contact).build();

        Guardian guardian1 = Guardian.builder().firstName("Alan").lastName("Smith").address(address1).contactDetail(guardianContactDetail1).build();
        Guardian guardian2 = Guardian.builder().firstName("Ruth").lastName("Jones").address(address2).contactDetail(guardianContactDetail2).build();

        //set students' tutors
        student1.setTeacher(teacher1);
        student2.setTeacher(teacher2);
        student3.setTeacher(teacher2);

        //set students' contact details and guardians
        student1.setContactDetail(guardianContactDetail1);
        student2.setContactDetail(guardianContactDetail2);
        student3.setContactDetail(guardianContactDetail2);

        //set Guardian 1 and student 1 relationships
        Set<Guardian> student1Guardians = new HashSet<>();
        student1Guardians.add(guardian1);
        student1.setGuardians(student1Guardians);

        Set<Student> guardian1Students = new HashSet<>();
        guardian1Students.add(student1);
        guardian1.setStudents(guardian1Students);

        //set Guardian 2 and students 2&3 relationships
        Set<Guardian> student2Guardians = new HashSet<>();
        Set<Student> guardian2Students = new HashSet<>();
        student2Guardians.add(guardian2);
        student2.setGuardians(student2Guardians);
        student3.setGuardians(student2Guardians);

        guardian2Students.add(student2);
        guardian2Students.add(student3);
        guardian2.setStudents(guardian2Students);

        guardianService.save(guardian1);
        guardianService.save(guardian2);
        System.out.println("Guardians loaded to DB...");

        //Pastoral (form) groups
        Set<Student> studentGroup1 = new HashSet<>();
        studentGroup1.add(student1);
        Set<Student> studentGroup2 = new HashSet<>();
        studentGroup1.add(student2);
        studentGroup2.add(student3);
        FormGroupList formGroupList1 = FormGroupList.builder().studentList(studentGroup1).groupName("Group 1").teacher(teacher1).build();
        FormGroupList formGroupList2 = FormGroupList.builder().studentList(studentGroup2).groupName("Group 2").teacher(teacher2).build();
        student1.setFormGroupList(formGroupList1);
        student2.setFormGroupList(formGroupList2);
        student3.setFormGroupList(formGroupList2);
        formGroupListService.save(formGroupList1);
        formGroupListService.save(formGroupList2);

        //academic details
        Subject subject1 = Subject.builder().subjectName("Math").build();
        Set<Teacher> teachers1 = new HashSet<>();
        teachers1.add(teacher1);
        subject1.setTeachers(teachers1);
        Set<Subject> teacher1subjects = new HashSet<>();
        teacher1subjects.add(subject1);
        teacher1.setSubjects(teacher1subjects);
        teacher1.setDepartment("Mathematics");

        Subject subject2 = Subject.builder().subjectName("English").build();
        Set<Teacher> teachers2 = new HashSet<>();
        teachers2.add(teacher2);
        subject2.setTeachers(teachers2);
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

        studentService.save(student1);
        studentService.save(student2);
        studentService.save(student3);
        System.out.println("Students loaded to DB...");

        System.out.println("Finished uploading to DB");
    }
}
