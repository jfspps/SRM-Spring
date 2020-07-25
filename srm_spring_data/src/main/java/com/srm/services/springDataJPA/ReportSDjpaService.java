package com.srm.services.springDataJPA;

import com.srm.model.academic.Report;
import com.srm.repositories.academicRepos.ReportRepository;
import com.srm.services.academicServices.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Profile("springDataJPA")
public class ReportSDjpaService implements ReportService {

    private final ReportRepository reportRepository;

    public ReportSDjpaService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Report findByStudentLastName(String lastName) {
        return reportRepository.findByStudent_LastName(lastName);
    }

    @Override
    public Report findByStudentFirstAndLastName(String firstName, String lastName) {
        return reportRepository.findByStudent_FirstNameAndStudent_LastName(firstName, lastName);
    }

    @Override
    public Report findByTeacherLastName(String lastName) {
        return reportRepository.findByTeacher_LastName(lastName);
    }

    @Override
    public Report findByTeacherFirstAndLastName(String firstName, String lastName) {
        return reportRepository.findByTeacher_FirstNameAndTeacher_LastName(firstName, lastName);
    }

    @Override
    public Report findBySubject(String subjectName) {
        return reportRepository.findBySubject_SubjectName(subjectName);
    }

    @Override
    public Report save(Report object) {
        return reportRepository.save(object);
    }

    @Override
    public Report findById(Long aLong) {
        return reportRepository.findById(aLong).orElse(null);
    }

    @Override
    public Set<Report> findAll() {
        Set<Report> reports = new HashSet<>();
        reportRepository.findAll().forEach(reports::add);
        return reports;
    }

    @Override
    public void delete(Report objectT) {
        reportRepository.delete(objectT);
    }

    @Override
    public void deleteById(Long aLong) {
        reportRepository.deleteById(aLong);
    }
}
