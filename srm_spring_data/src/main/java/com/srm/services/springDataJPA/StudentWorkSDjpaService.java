package com.srm.services.springDataJPA;

import com.srm.model.academic.StudentWork;
import com.srm.repositories.academicRepos.StudentWorkRepository;
import com.srm.services.academicServices.StudentWorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Profile("springDataJPA")
public class StudentWorkSDjpaService implements StudentWorkService {

    private final StudentWorkRepository studentWorkRepository;

    public StudentWorkSDjpaService(StudentWorkRepository studentWorkRepository) {
        this.studentWorkRepository = studentWorkRepository;
    }

    @Override
    public StudentWork findByTitle(String title) {
        return studentWorkRepository.findByTitle(title);
    }

    @Override
    public StudentWork findByTeacherLastName(String lastName) {
        return studentWorkRepository.findByTeacherUploader_LastName(lastName);
    }

    @Override
    public StudentWork findByTeacherFirstAndLastName(String firstName, String lastName) {
        return studentWorkRepository.findByTeacherUploader_FirstNameAndTeacherUploader_LastName(firstName, lastName);
    }

    @Override
    public StudentWork findBySubject(String subjectName) {
        return studentWorkRepository.findBySubject_SubjectName(subjectName);
    }

    @Override
    public StudentWork findByDescription(String description) {
        return studentWorkRepository.findByAssignmentType_Description(description);
    }

    @Override
    public StudentWork findByContribution(boolean isAContributor) {
        return studentWorkRepository.findByContributor(isAContributor);
    }

    @Override
    public StudentWork save(StudentWork object) {
        return studentWorkRepository.save(object);
    }

    @Override
    public StudentWork findById(Long aLong) {
        return studentWorkRepository.findById(aLong).orElse(null);
    }

    @Override
    public Set<StudentWork> findAll() {
        Set<StudentWork> studentWorks = new HashSet<>();
        studentWorkRepository.findAll().forEach(studentWorks::add);
        return studentWorks;
    }

    @Override
    public void delete(StudentWork objectT) {
        studentWorkRepository.delete(objectT);
    }

    @Override
    public void deleteById(Long aLong) {
        studentWorkRepository.deleteById(aLong);
    }
}
