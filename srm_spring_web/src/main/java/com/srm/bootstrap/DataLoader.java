package com.srm.bootstrap;

import com.srm.model.academic.Subject;
import com.srm.model.people.Guardian;
import com.srm.model.people.Student;
import com.srm.model.people.Teacher;
import com.srm.services.GuardianService;
import com.srm.services.StudentService;
import com.srm.services.SubjectService;
import com.srm.services.TeacherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
        studentService.save(student1);

        Student student2 = Student.builder().firstName("Elizabeth").lastName("Jones").build();
        studentService.save(student2);

        System.out.println("Students loaded to DB...");

        Teacher teacher1 = Teacher.builder().firstName("Keith").lastName("Thomson").build();
        teacherService.save(teacher1);

        Teacher teacher2 = new Teacher();
        teacher2.setFirstName("Julie");
        teacher2.setLastName("Adams");
        teacherService.save(teacher2);

        System.out.println("Teachers loaded to DB...");

        Guardian guardian1 = Guardian.builder().firstName("Alan").lastName("Smith").build();
        guardianService.save(guardian1);

        Guardian guardian2 = new Guardian();
        guardian2.setFirstName("Ruth");
        guardian2.setLastName("Jones");
        guardianService.save(guardian2);

        System.out.println("Guardians loaded to DB...");

        Subject subject1 = new Subject();
        subject1.setSubjectName("Math");
        subjectService.save(subject1);

        Subject subject2 = new Subject();
        subject2.setSubjectName("English");
        subjectService.save(subject2);

        System.out.println("Subjects loaded to DB...");
        System.out.println("Finished uploading to DB");
    }
}
