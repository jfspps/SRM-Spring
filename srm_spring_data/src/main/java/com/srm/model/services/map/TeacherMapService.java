package com.srm.model.services.map;

import com.srm.model.people.Teacher;
import com.srm.model.services.TeacherService;
import org.springframework.stereotype.Service;

import java.util.Set;

//instruct Spring to inject the CRUD TeacherService into the application context as a bean
//note that neither AbstractService nor any of the services (TeacherService) is declared with @Service; the wiring is
//done through the map service
@Service
public class TeacherMapService extends AbstractMapService<Teacher, Long> implements TeacherService {

    //map service which links the BaseService CRUD ops (via TeacherService) with AbstractMapService
    //generally, take a Teacher object from MapService and return AbstractMapService's method (hence super)

    @Override
    public Teacher save(Teacher teacher) {
        return super.save(teacher);
    }

    @Override
    public Teacher findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Set<Teacher> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(Teacher teacher) {
        super.delete(teacher);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    //unique to the TeacherService interface
    @Override
    public Teacher findByName(String name) {
        return null;
    }
}
