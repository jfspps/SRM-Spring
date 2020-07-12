package com.srm.model.services.map;

import com.srm.model.academic.Subject;
import com.srm.model.services.SubjectService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
//@Profile({"default", "map"})
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
