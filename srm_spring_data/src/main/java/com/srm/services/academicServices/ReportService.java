package com.srm.services.academicServices;

import com.srm.model.academic.Report;
import com.srm.services.CrudService;

public interface ReportService extends CrudService<Report, Long> {

    //other methods not declared in CrudService

    Report findByStudentLastName(String lastName);

    Report findByStudentFirstAndLastName(String firstName, String lastName);

    Report findByTeacherLastName(String lastName);

    Report findByTeacherFirstAndLastName(String firstName, String lastName);

    Report findBySubject(String subjectName);

    // there are numerous other search parameters to apply
}
