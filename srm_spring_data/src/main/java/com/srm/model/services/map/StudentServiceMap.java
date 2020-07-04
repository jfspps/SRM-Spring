package com.srm.model.services.map;

import com.srm.model.people.Student;
import com.srm.model.services.BaseService;

import java.util.Set;

public class StudentServiceMap extends AbstractMapService<Student, Long> implements BaseService<Student, Long> {

    //map service which links the BaseService CRUD ops with AbstractMapService specifically for Student
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
}