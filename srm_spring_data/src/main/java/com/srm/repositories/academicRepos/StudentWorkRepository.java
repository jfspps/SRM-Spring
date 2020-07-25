package com.srm.repositories.academicRepos;


import com.srm.model.academic.StudentWork;
import org.springframework.data.repository.CrudRepository;

//the implementation of the following methods is supplied automatically by JPA

//declares additional SpringDataJPA CRUD functionality for StudentWork
public interface StudentWorkRepository extends CrudRepository<StudentWork, Long> {

    StudentWork findByTitle(String title);

    //method identifier auto-generated in IDE...
    StudentWork findByTeacherUploader_LastName(String uploaderLastName);

    StudentWork findByTeacherUploader_FirstNameAndTeacherUploader_LastName(String firstName, String lastName);

    StudentWork findBySubject_SubjectName(String subjectName);

    StudentWork findByAssignmentType_Description(String description);

    //method identifier auto-generated in IDE...
    StudentWork findByContributor(boolean isAContributor);
}
