package com.srm.services.map;

import com.srm.exceptions.NotFoundException;
import com.srm.model.academic.Subject;
import com.srm.services.academicServices.SubjectService;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@NoArgsConstructor
@Profile("map")
public class SubjectMapService extends AbstractMapService<Subject, Long> implements SubjectService {

    @Override
    public Subject findBySubjectName(String subjectName) {
        return this.findAll()
                .stream()
                .filter(subject -> subject.getSubjectName().equalsIgnoreCase(subjectName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Subject save(Subject subject) {
        if (subject != null) {
            return super.save(subject);
        } else {
            System.out.println("Empty object passed to Subject()");
            return null;
        }
    }

    @Override
    public Subject findById(Long id) {
        Optional<Subject> optional = Optional.ofNullable(super.findById(id));
        if (optional.isEmpty()){
            throw new NotFoundException("Subject not found with ID: " + id);
        }
        return optional.get();
    }

    @Override
    public Set<Subject> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(Subject objectT) {
        super.delete(objectT);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}
