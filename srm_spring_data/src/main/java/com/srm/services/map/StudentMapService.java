package com.srm.services.map;

import com.srm.model.people.Student;
import com.srm.services.peopleServices.StudentService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

//instruct Spring to inject the CRUD StudentService into the application context as a bean
//note that neither AbstractService nor any of the services (StudentService) is declared with @Service; the wiring is
//done through the map service
@Slf4j
@Service
@NoArgsConstructor
//this service-map is also the default
@Profile(value = {"default", "map"})
public class StudentMapService extends AbstractMapService<Student, Long> implements StudentService {

    //map service which links the BaseService CRUD ops (via StudentService) with AbstractMapService
    //generally, take a Student object from MapService and return AbstractMapService's method (hence super)

    @Override
    public Student save(Student student) {
        if (student != null) {
            return super.save(student);
        } else
            System.out.println("Empty object passed to Student()");
        return null;
    }

    @Override
    public Student findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Set<Student> findAll() {
        log.info("findAll() from HashMap services");
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
    public Student findByLastName(String lastName) {
        return this.findAll()
                .stream()
                .filter(student -> student.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Student findByFirstAndLastName(String firstName, String lastName) {
        return this.findAll()
                .stream()
                .filter(student -> student.getLastName().equalsIgnoreCase(lastName))
                .filter(student -> student.getFirstName().equalsIgnoreCase(firstName))
                .findFirst()
                .orElse(null);
    }
}