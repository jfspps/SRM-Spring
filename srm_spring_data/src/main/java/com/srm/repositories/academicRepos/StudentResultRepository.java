package com.srm.repositories.academicRepos;


//the implementation of the following methods is supplied automatically by JPA

import com.srm.model.academic.StudentResult;
import org.springframework.data.repository.CrudRepository;

//declares additional SpringDataJPA CRUD functionality for StudentResult
public interface StudentResultRepository extends CrudRepository<StudentResult, Long> {

    StudentResult findByStudent_LastName(String studentLastName);

    StudentResult findByStudent_FirstNameAndStudent_LastName(String firstName, String lastName);

    StudentResult findByTeacher_LastName(String markerLastName);

    StudentResult findByTeacher_FirstNameAndTeacher_LastName(String firstName, String lastName);

    StudentResult findByStudentWork_Title(String assignmentTitle);

    StudentResult findByScore(String score);
}
