package com.srm.services.map;

import com.srm.model.academic.AssignmentType;
import com.srm.services.academicServices.AssignmentTypeService;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@NoArgsConstructor
@Profile("map")
public class AssignmentTypeMapService extends AbstractMapService<AssignmentType, Long> implements AssignmentTypeService {

    @Override
    public AssignmentType findByDescription(String type) {
        return this.findAll()
                .stream()
                .filter(assignmentType -> assignmentType.getDescription().equalsIgnoreCase(type))
                .findFirst()
                .orElse(null);
    }

    @Override
    public AssignmentType save(AssignmentType assignmentType) {
        if (assignmentType != null) {
            return super.save(assignmentType);
        } else {
            System.out.println("Empty object passed to AssignmentType()");
            return null;
        }
    }

    @Override
    public AssignmentType findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Set<AssignmentType> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(AssignmentType objectT) {
        super.delete(objectT);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}
