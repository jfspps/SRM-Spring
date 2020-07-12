package com.srm.model.services.springDataJPA;

import com.srm.model.academic.Subject;
import com.srm.model.repositories.SubjectRepository;
import com.srm.model.services.SubjectService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

//see StudentSDjpaService for commentary
@Service
@Profile("springDataJPA")
public class SubjectSDjpaService implements SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectSDjpaService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Subject findBySubjectName(String subjectName) {
        return subjectRepository.findBySubjectName(subjectName);
    }

    @Override
    public Subject save(Subject object) {
        return subjectRepository.save(object);
    }

    @Override
    public Subject findById(Long aLong) {
        return subjectRepository.findById(aLong).orElse(null);
    }

    @Override
    public Set<Subject> findAll() {
        Set<Subject> subjects = new HashSet<>();
        subjectRepository.findAll().forEach(subjects::add);
        return subjects;
    }

    @Override
    public void delete(Subject objectT) {
        subjectRepository.delete(objectT);
    }

    @Override
    public void deleteById(Long aLong) {
        subjectRepository.deleteById(aLong);
    }
}
