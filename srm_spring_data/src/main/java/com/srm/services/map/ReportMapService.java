package com.srm.services.map;


import com.srm.model.academic.Report;
import com.srm.services.academicServices.ReportService;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@NoArgsConstructor
//this service-map is also the default
@Profile(value = {"default", "map"})
public class ReportMapService extends AbstractMapService<Report, Long> implements ReportService {

    @Override
    public Report findByStudentLastName(String lastName) {
        return this.findAll()
                .stream()
                .filter(report -> report.getStudent().getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Report findByStudentFirstAndLastName(String firstName, String lastName) {
        return this.findAll()
                .stream()
                .filter(report -> report.getStudent().getLastName().equalsIgnoreCase(lastName))
                .filter(report -> report.getStudent().getFirstName().equalsIgnoreCase(firstName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Report findByTeacherLastName(String lastName) {
        return this.findAll()
                .stream()
                .filter(report -> report.getTeacher().getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Report findByTeacherFirstAndLastName(String firstName, String lastName) {
        return this.findAll()
                .stream()
                .filter(report -> report.getTeacher().getLastName().equalsIgnoreCase(lastName))
                .filter(report -> report.getTeacher().getFirstName().equalsIgnoreCase(firstName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Report findBySubject(String subjectName) {
        return this.findAll()
                .stream()
                .filter(report -> report.getSubject().getSubjectName().equalsIgnoreCase(subjectName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Report save(Report report) {
        if (report != null) {
            return super.save(report);
        } else {
            System.out.println("Empty object passed to Report()");
            return null;
        }
    }

    @Override
    public Report findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Set<Report> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(Report objectT) {
        super.delete(objectT);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}
