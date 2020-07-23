package com.srm.services.academicServices;

import com.srm.model.academic.StudentResult;
import com.srm.model.academic.StudentWork;
import com.srm.model.people.Student;
import com.srm.model.people.Teacher;
import com.srm.services.CrudService;

public interface StudentResultService extends CrudService<StudentResult, Long> {

    //other methods not declared in CrudService

    StudentResult findByStudent(Student student);

    StudentResult findByMarker(Teacher marker);

    StudentResult findByStudentWork(StudentWork studentWork);

    StudentResult findByScore(String score);

    //numerous other possible search parameters possible
}
