package com.srm.bootstrap;

import com.srm.model.people.Guardian;
import com.srm.model.people.Student;
import com.srm.model.people.Teacher;
import com.srm.model.services.GuardianService;
import com.srm.model.services.StudentService;
import com.srm.model.services.TeacherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//set as a Spring Bean with @Component, and run run()
@Component
public class DataLoader implements CommandLineRunner {

    //interfaces
    private final GuardianService guardianService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    //implement dependency inversion (@Autowired annotation not required) offered since each service is annotated
    public DataLoader(GuardianService guardianService, StudentService studentService, TeacherService teacherService) {
        this.guardianService = guardianService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @Override
    public void run(String... args) throws Exception {
        //build a temporary POJO from Student, Teacher and Guardian classes and add (inject) to each respective service

        Student student1 = new Student();
        student1.setFirstName("John");
        student1.setLastName("Smith");
        studentService.save(student1);

        Student student2 = new Student();
        student2.setFirstName("Elizabeth");
        student2.setLastName("Jones");
        studentService.save(student2);

        System.out.println("Students loaded to DB...");

        Teacher teacher1 = new Teacher();
        teacher1.setFirstName("Keith");
        teacher1.setLastName("Thomson");
        teacherService.save(teacher1);

        Teacher teacher2 = new Teacher();
        teacher2.setFirstName("Julie");
        teacher2.setLastName("Adams");
        teacherService.save(teacher2);

        System.out.println("Teachers loaded to DB...");

        Guardian guardian1 = new Guardian();
        guardian1.setFirstName("Alan");
        guardian1.setLastName("Smith");
        guardianService.save(guardian1);

        Guardian guardian2 = new Guardian();
        guardian2.setFirstName("Ruth");
        guardian2.setLastName("Jones");
        guardianService.save(guardian2);

        System.out.println("Guardians loaded to DB...");
        System.out.println("Finished uploading to DB");
    }
}
