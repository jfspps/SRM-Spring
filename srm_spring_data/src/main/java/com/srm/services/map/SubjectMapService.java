package com.srm.services.map;

import com.srm.model.academic.Subject;
import com.srm.services.SubjectService;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@NoArgsConstructor
//this service-map is also the default
@Profile(value = {"default", "map"})
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
        return super.findById(id);
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
