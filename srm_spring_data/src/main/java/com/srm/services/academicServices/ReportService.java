package com.srm.services.academicServices;

import com.srm.model.academic.Report;
import com.srm.model.academic.Subject;
import com.srm.model.people.Student;
import com.srm.model.people.Teacher;
import com.srm.services.CrudService;

public interface ReportService extends CrudService<Report, Long> {

    //other methods not declared in CrudService

    Report findByStudent(Student student);

    Report findByTeacher(Teacher teacher);

    Report findBySubject(Subject subject);

    // there are numerous other search parameters to apply
}
