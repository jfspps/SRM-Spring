package com.srm.model.services.map;

import com.srm.model.people.Student;
import com.srm.model.services.StudentService;
import org.springframework.stereotype.Service;

import java.util.Set;

//instruct Spring to inject the CRUD StudentService into the application context as a bean
@Service
public class StudentServiceMap extends AbstractMapService<Student, Long> implements StudentService {

    //map service which links the BaseService CRUD ops (via StudentService) with AbstractMapService
    //generally, take a Student object from MapService and return AbstractMapService's method (hence super)

    @Override
    public Student save(Student student) {
        return super.save(student.getId(), student);
    }

    @Override
    public Student findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Set<Student> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(Student student) {
        super.delete(student);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    //unique to the StudentService interface
    @Override
    public Student findByName(String name) {
        return null;
    }
}