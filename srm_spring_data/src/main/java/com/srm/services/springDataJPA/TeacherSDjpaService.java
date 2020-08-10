package com.srm.services.springDataJPA;

import com.srm.exceptions.NotFoundException;
import com.srm.model.people.Student;
import com.srm.model.people.Teacher;
import com.srm.repositories.peopleRepos.TeacherRepository;
import com.srm.services.peopleServices.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
    public List<Teacher> findAllByLastNameLike(String lastName) {
        return teacherRepository.findAllByLastNameLike(lastName);
    }

    @Override
    public List<Teacher> findAllByFirstNameLikeAndLastNameLike(String firstName, String lastName) {
        return teacherRepository.findAllByFirstNameLikeAndLastNameLike(firstName, lastName);
    }

    @Override
    public Teacher findByFirstNameAndLastNameAndDepartment(String firstName, String lastName, String department) {
        return teacherRepository.findByFirstNameAndLastNameAndDepartment(firstName, lastName, department);
    }

    @Override
    public Teacher save(Teacher object) {
        return teacherRepository.save(object);
    }

    @Override
    public Teacher findById(Long aLong) {
        Optional<Teacher> optional = teacherRepository.findById(aLong);
        if (optional.isEmpty()){
            throw new NotFoundException("Teacher not found");
        }
        return optional.get();
    }

    @Override
    public Set<Teacher> findAll() {
        Set<Teacher> teachers = new HashSet<>();
        teacherRepository.findAll().forEach(teachers::add);
        return teachers;
    }

    @Override
    public List<Teacher> findAllByDepartment(String department) {
        return teacherRepository.findAllByDepartmentLike(department);
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
