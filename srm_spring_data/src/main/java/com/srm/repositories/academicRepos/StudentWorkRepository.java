package com.srm.repositories.academicRepos;


import com.srm.model.academic.StudentWork;
import org.springframework.data.repository.CrudRepository;

//the implementation of the following methods is supplied automatically by JPA

//declares additional SpringDataJPA CRUD functionality for StudentWork
public interface StudentWorkRepository extends CrudRepository<StudentWork, Long> {

    StudentWork findByTitle(String title);

    //method identifier auto-generated in IDE...
    StudentWork findByTeacherUploader_LastName(String uploaderLastName);

    StudentWork findBySubject_SubjectName(String subjectName);

    StudentWork findByAssignmentType_AssignmentType(String assignmentType);

    //method identifier auto-generated in IDE...
    StudentWork findByContributor(boolean isAContributor);
}
