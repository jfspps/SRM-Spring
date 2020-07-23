package com.srm.repositories.academicRepos;


import com.srm.model.academic.AssignmentType;
import com.srm.model.academic.StudentWork;
import com.srm.model.academic.Subject;
import com.srm.model.people.Teacher;
import org.springframework.data.repository.CrudRepository;

//the implementation of the following methods is supplied automatically by JPA

//declares additional SpringDataJPA CRUD functionality for StudentWork
public interface StudentWorkRepository extends CrudRepository<StudentWork, Long> {

    StudentWork findByTitle(String title);

    //method identifier auto-generated in IDE...
    StudentWork findByTeacherUploader(Teacher uploader);

    StudentWork findBySubject(Subject subject);

    StudentWork findByAssignmentType(AssignmentType assignmentType);

    //method identifier auto-generated in IDE...
    StudentWork findByContributor(boolean isAContributor);
}
