package com.srm.services.springDataJPA;

import com.srm.model.people.Student;
import com.srm.repositories.peopleRepos.StudentRepository;
import com.srm.services.peopleServices.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

// both StudentSDjpaService and StudentServiceMap are declared as Services for StudentService, so set a profile to
// pick one which operates as runtime (eventually, this dependency is decided later)

// SpringData JPA is only injected if application.yml is set to springDataJPA

// The interface StudentService declares the minimum CRUD functionality and custom findBy...() methods
// The interface StudentRepository declares the methods which extends from Spring CrudRepository (automatically
// implemented by JPA)
@Slf4j
@Service
@Profile("springDataJPA")
public class StudentSDjpaService implements StudentService {

    private final StudentRepository studentRepository;

    public StudentSDjpaService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student findByLastName(String lastName) {
        return studentRepository.findByLastName(lastName);
    }

    @Override
    public Student save(Student object) {
        return studentRepository.save(object);
    }

    @Override
    public Student findById(Long aLong) {
        //findById returns an Optional<> which is never null (aLong can be null)
        //if none found with given aLong then return null
        return studentRepository.findById(aLong).orElse(null);
    }

    @Override
    public Set<Student> findAll() {
        log.info("findAll() from Spring Data JPA services");
        Set<Student> students = new HashSet<>();
        //pass each student from the repo to students and return
        studentRepository.findAll().forEach(students::add);
        return students;
    }

    @Override
    public void delete(Student objectT) {
        studentRepository.delete(objectT);
    }

    @Override
    public void deleteById(Long aLong) {
        studentRepository.deleteById(aLong);
    }
}
