package com.srm.repositories.academicRepos;


import com.srm.model.academic.Report;
import org.springframework.data.repository.CrudRepository;

//the implementation of the following methods is supplied automatically by JPA

//declares additional SpringDataJPA CRUD functionality for Report
public interface ReportRepository extends CrudRepository<Report, Long> {

    Report findByStudent_LastName(String studentLastName);

    Report findByStudent_FirstNameAndStudent_LastName(String firstName, String lastName);

    Report findByTeacher_LastName(String teacherLastName);

    Report findByTeacher_FirstNameAndTeacher_LastName(String firstName, String lastName);

    Report findBySubject_SubjectName(String subjectName);
}
