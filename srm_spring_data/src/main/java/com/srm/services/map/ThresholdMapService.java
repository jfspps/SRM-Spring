package com.srm.services.map;

import com.srm.model.academic.Threshold;
import com.srm.services.academicServices.ThresholdService;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@NoArgsConstructor
//this service-map is also the default
@Profile(value = {"default", "map"})
public class ThresholdMapService extends AbstractMapService<Threshold, Long> implements ThresholdService {

    @Override
    public Threshold findByNumericalBoundary(int numericalBoundary) {
        return this.findAll()
                .stream()
                .filter(threshold -> threshold.getNumerical() == numericalBoundary)
                .findFirst()
                .orElse(null);
    }

    //compares letter grade case: "A" != "a" etc.
    @Override
    public Threshold findByLetterGrade(String letterGrade) {
        return this.findAll()
                .stream()
                .filter(threshold -> threshold.getAlphabetical().equals(letterGrade))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Threshold save(Threshold threshold) {
        if (threshold != null) {
            return super.save(threshold);
        } else {
            System.out.println("Empty object passed to Threshold()");
            return null;
        }
    }

    @Override
    public Threshold findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Set<Threshold> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(Threshold objectT) {
        super.delete(objectT);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}
