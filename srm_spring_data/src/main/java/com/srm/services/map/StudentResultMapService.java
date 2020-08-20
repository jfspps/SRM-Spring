package com.srm.services.map;


import com.srm.model.academic.StudentResult;
import com.srm.services.academicServices.StudentResultService;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@NoArgsConstructor
@Profile("map")
public class StudentResultMapService extends AbstractMapService<StudentResult, Long> implements StudentResultService {

    @Override
    public StudentResult findByStudentLastName(String lastName) {
        return this.findAll()
                .stream()
                .filter(studentResult -> studentResult.getStudent().getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public StudentResult findByTeacherLastName(String lastName) {
        return this.findAll()
                .stream()
                .filter(studentResult -> studentResult.getTeacher().getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public StudentResult findByStudentFirstAndLastName(String firstName, String lastName) {
        return this.findAll()
                .stream()
                .filter(studentResult -> studentResult.getStudent().getLastName().equalsIgnoreCase(lastName))
                .filter(studentResult -> studentResult.getStudent().getFirstName().equalsIgnoreCase(firstName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public StudentResult findByTeacherFirstAndLastName(String firstName, String lastName) {
        return this.findAll()
                .stream()
                .filter(studentResult -> studentResult.getTeacher().getLastName().equalsIgnoreCase(lastName))
                .filter(studentResult -> studentResult.getTeacher().getFirstName().equalsIgnoreCase(firstName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public StudentResult findByStudentWorkTitle(String assignmentTitle) {
        return this.findAll()
                .stream()
                .filter(studentResult -> studentResult.getStudentWork().getTitle().equalsIgnoreCase(assignmentTitle))
                .findFirst()
                .orElse(null);
    }

    @Override
    public StudentResult findByScore(String score) {
        return this.findAll()
                .stream()
                .filter(studentResult -> studentResult.getScore().equalsIgnoreCase(score))
                .findFirst()
                .orElse(null);
    }

    @Override
    public StudentResult save(StudentResult studentResult) {
        if (studentResult != null) {
            return super.save(studentResult);
        } else {
            System.out.println("Empty object passed to StudentResult()");
            return null;
        }
    }

    @Override
    public StudentResult findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Set<StudentResult> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(StudentResult objectT) {
        super.delete(objectT);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}
