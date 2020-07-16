package com.srm.services.map;

import com.srm.model.academic.Subject;
import com.srm.services.SubjectService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
//this service-map is also the default
@Profile({"default", "map"})
public class SubjectMapService extends AbstractMapService<Subject, Long> implements SubjectService {

    @Override
    public Subject findBySubjectName(String subjectName) {
        return null;
    }

    @Override
    public Subject save(Subject object) {
        return super.save(object);
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
