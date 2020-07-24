package com.srm.services.map;

import com.srm.model.academic.StudentWork;
import com.srm.services.academicServices.StudentWorkService;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@NoArgsConstructor
//this service-map is also the default
@Profile(value = {"default", "map"})
public class StudentWorkMapService extends AbstractMapService<StudentWork, Long> implements StudentWorkService {

    @Override
    public StudentWork findByTitle(String title) {
        return this.findAll()
                .stream()
                .filter(studentWork -> studentWork.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    @Override
    public StudentWork findByTeacherLastName(String lastName) {
        return this.findAll()
                .stream()
                .filter(studentWork -> studentWork.getTeacherUploader().getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public StudentWork findBySubject(String subjectName) {
        return this.findAll()
                .stream()
                .filter(studentWork -> studentWork.getSubject().getSubjectName().equalsIgnoreCase(subjectName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public StudentWork findByAssignmentType(String assignmentType) {
        return this.findAll()
                .stream()
                .filter(studentWork -> studentWork.getAssignmentType().getAssignmentType().equalsIgnoreCase(assignmentType))
                .findFirst()
                .orElse(null);
    }

    @Override
    public StudentWork findByContribution(boolean isAContributor) {
        return this.findAll()
                .parallelStream()
                .filter(StudentWork::isContributor)
                .findFirst()
                .orElse(null);
    }

    @Override
    public StudentWork save(StudentWork studentWork) {
        if (studentWork != null) {
            return super.save(studentWork);
        } else {
            System.out.println("Empty object passed to StudentWork()");
            return null;
        }
    }

    @Override
    public StudentWork findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Set<StudentWork> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(StudentWork objectT) {
        super.delete(objectT);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}
