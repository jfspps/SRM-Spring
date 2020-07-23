package com.srm.services.map;

import com.srm.model.people.Teacher;
import com.srm.services.peopleServices.TeacherService;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

//instruct Spring to inject the CRUD TeacherService into the application context as a bean
//note that neither AbstractService nor any of the services (TeacherService) is declared with @Service; the wiring is
//done through the map service
@Service
@NoArgsConstructor
//this service-map is also the default
@Profile(value = {"default", "map"})
public class TeacherMapService extends AbstractMapService<Teacher, Long> implements TeacherService {

    //map service which links the BaseService CRUD ops (via TeacherService) with AbstractMapService
    //generally, take a Teacher object from MapService and return AbstractMapService's method (hence super)

    @Override
    public Teacher save(Teacher teacher) {
        if (teacher != null) {
            return super.save(teacher);
        } else {
            System.out.println("Empty object passed to Teacher()");
            return null;
        }
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
    public Teacher findByLastName(String lastName) {
        return this.findAll()
                .stream()
                .filter(teacher -> teacher.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Teacher findByFirstAndLastName(String firstName, String lastName) {
        return this.findAll()
                .stream()
                .filter(teacher -> teacher.getLastName().equalsIgnoreCase(lastName))
                .filter(teacher -> teacher.getFirstName().equalsIgnoreCase(firstName))
                .findFirst()
                .orElse(null);
    }
}
