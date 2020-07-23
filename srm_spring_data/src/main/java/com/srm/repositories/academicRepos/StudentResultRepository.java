package com.srm.repositories.academicRepos;


//the implementation of the following methods is supplied automatically by JPA

import com.srm.model.academic.StudentResult;
import com.srm.model.academic.StudentWork;
import com.srm.model.people.Student;
import com.srm.model.people.Teacher;
import org.springframework.data.repository.CrudRepository;

//declares additional SpringDataJPA CRUD functionality for StudentResult
public interface StudentResultRepository extends CrudRepository<StudentResult, Long> {

    StudentResult findByStudent(Student student);

    StudentResult findByTeacher(Teacher marker);

    StudentResult findByStudentWork(StudentWork studentWork);

    StudentResult findByScore(String score);
}
