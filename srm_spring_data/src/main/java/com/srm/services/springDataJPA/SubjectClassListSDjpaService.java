package com.srm.services.springDataJPA;

import com.srm.model.people.SubjectClassList;
import com.srm.repositories.peopleRepos.SubjectClassListRepository;
import com.srm.services.peopleServices.SubjectClassListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Profile("springDataJPA")
public class SubjectClassListSDjpaService implements SubjectClassListService {

    private final SubjectClassListRepository subjectClassListRepository;

    public SubjectClassListSDjpaService(SubjectClassListRepository subjectClassListRepository) {
        this.subjectClassListRepository = subjectClassListRepository;
    }

    @Override
    public SubjectClassList findBySubject(String subjectName) {
        return subjectClassListRepository.findBySubject_SubjectName(subjectName);
    }

    @Override
    public SubjectClassList findByTeacherLastName(String lastName) {
        return subjectClassListRepository.findByTeacher_LastName(lastName);
    }

    @Override
    public SubjectClassList findByGroupName(String groupName) {
        return subjectClassListRepository.findByGroupName(groupName);
    }

    @Override
    public SubjectClassList findByTeacherFirstAndLastName(String firstName, String lastName) {
        return subjectClassListRepository.findByTeacher_FirstNameAndTeacher_LastName(firstName, lastName);
    }

    @Override
    public SubjectClassList save(SubjectClassList object) {
        return subjectClassListRepository.save(object);
    }

    @Override
    public SubjectClassList findById(Long aLong) {
        return subjectClassListRepository.findById(aLong).orElse(null);
    }

    @Override
    public Set<SubjectClassList> findAll() {
        Set<SubjectClassList> subjectClassLists = new HashSet<>();
        subjectClassListRepository.findAll().forEach(subjectClassLists::add);
        return subjectClassLists;
    }

    @Override
    public void delete(SubjectClassList objectT) {
        subjectClassListRepository.delete(objectT);
    }

    @Override
    public void deleteById(Long aLong) {
        subjectClassListRepository.deleteById(aLong);
    }
}
