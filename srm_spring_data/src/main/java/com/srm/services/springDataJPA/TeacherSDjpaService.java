package com.srm.services.springDataJPA;

import com.srm.model.people.Teacher;
import com.srm.repositories.peopleRepos.TeacherRepository;
import com.srm.services.peopleServices.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

//see StudentSDjpaService for commentary
@Slf4j
@Service
@Profile("springDataJPA")
public class TeacherSDjpaService implements TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherSDjpaService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Teacher findByLastName(String lastName) {
        return teacherRepository.findByLastName(lastName);
    }

    @Override
    public Teacher findByFirstAndLastName(String firstName, String lastName) {
        return teacherRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Teacher save(Teacher object) {
        return teacherRepository.save(object);
    }

    @Override
    public Teacher findById(Long aLong) {
        return teacherRepository.findById(aLong).orElse(null);
    }

    @Override
    public Set<Teacher> findAll() {
        Set<Teacher> teachers = new HashSet<>();
        teacherRepository.findAll().forEach(teachers::add);
        return teachers;
    }

    @Override
    public void delete(Teacher objectT) {
        teacherRepository.delete(objectT);
    }

    @Override
    public void deleteById(Long aLong) {
        teacherRepository.deleteById(aLong);
    }
}
