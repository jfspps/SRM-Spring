package com.srm.services.springDataJPA;

import com.srm.model.academic.AssignmentType;
import com.srm.repositories.academicRepos.AssignmentTypeRepository;
import com.srm.services.academicServices.AssignmentTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Profile("springDataJPA")
public class AssignmentTypeSDjpaService implements AssignmentTypeService {

    private final AssignmentTypeRepository assignmentTypeRepository;

    public AssignmentTypeSDjpaService(AssignmentTypeRepository assignmentTypeRepository) {
        this.assignmentTypeRepository = assignmentTypeRepository;
    }

    @Override
    public AssignmentType findByDescription(String description) {
        return assignmentTypeRepository.findByDescription(description);
    }

    @Override
    public AssignmentType save(AssignmentType object) {
        return assignmentTypeRepository.save(object);
    }

    @Override
    public AssignmentType findById(Long aLong) {
        return assignmentTypeRepository.findById(aLong).orElse(null);
    }

    @Override
    public Set<AssignmentType> findAll() {
        Set<AssignmentType> assignmentTypes = new HashSet<>();
        assignmentTypeRepository.findAll().forEach(assignmentTypes::add);
        return assignmentTypes;
    }

    @Override
    public void delete(AssignmentType objectT) {
        assignmentTypeRepository.delete(objectT);
    }

    @Override
    public void deleteById(Long aLong) {
        assignmentTypeRepository.deleteById(aLong);
    }
}
