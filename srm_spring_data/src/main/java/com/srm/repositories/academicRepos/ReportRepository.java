package com.srm.repositories.academicRepos;


import com.srm.model.academic.Report;
import com.srm.model.academic.Subject;
import com.srm.model.people.Student;
import com.srm.model.people.Teacher;
import org.springframework.data.repository.CrudRepository;

//the implementation of the following methods is supplied automatically by JPA

//declares additional SpringDataJPA CRUD functionality for Report
public interface ReportRepository extends CrudRepository<Report, Long> {

    Report findByStudent(Student student);

    Report findByTeacher(Teacher teacher);

    Report findBySubject(Subject subject);
}
