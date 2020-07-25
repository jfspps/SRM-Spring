package com.srm.services.springDataJPA;

import com.srm.model.academic.StudentResult;
import com.srm.repositories.academicRepos.StudentResultRepository;
import com.srm.services.academicServices.StudentResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Profile("springDataJPA")
public class StudentResultSDjpaService implements StudentResultService {

    private final StudentResultRepository studentResultRepository;

    public StudentResultSDjpaService(StudentResultRepository studentResultRepository) {
        this.studentResultRepository = studentResultRepository;
    }

    @Override
    public StudentResult findByStudentLastName(String lastName) {
        return studentResultRepository.findByStudent_LastName(lastName);
    }

    @Override
    public StudentResult findByStudentFirstAndLastName(String firstName, String lastName) {
        return studentResultRepository.findByStudent_FirstNameAndStudent_LastName(firstName, lastName);
    }

    @Override
    public StudentResult findByTeacherLastName(String lastName) {
        return studentResultRepository.findByTeacher_LastName(lastName);
    }

    @Override
    public StudentResult findByTeacherFirstAndLastName(String firstName, String lastName) {
        return studentResultRepository.findByTeacher_FirstNameAndTeacher_LastName(firstName, lastName);
    }

    @Override
    public StudentResult findByStudentWorkTitle(String assignmentTitle) {
        return studentResultRepository.findByStudentWork_Title(assignmentTitle);
    }

    @Override
    public StudentResult findByScore(String score) {
        return studentResultRepository.findByScore(score);
    }

    @Override
    public StudentResult save(StudentResult object) {
        return studentResultRepository.save(object);
    }

    @Override
    public StudentResult findById(Long aLong) {
        return studentResultRepository.findById(aLong).orElse(null);
    }

    @Override
    public Set<StudentResult> findAll() {
        Set<StudentResult> studentResults = new HashSet<>();
        studentResultRepository.findAll().forEach(studentResults::add);
        return studentResults;
    }

    @Override
    public void delete(StudentResult objectT) {
        studentResultRepository.delete(objectT);
    }

    @Override
    public void deleteById(Long aLong) {
        studentResultRepository.deleteById(aLong);
    }
}
