package com.srm.services.springDataJPA;

import com.srm.model.academic.Threshold;
import com.srm.repositories.academicRepos.ThresholdRepository;
import com.srm.services.academicServices.ThresholdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

//see StudentSDjpaService for commentary
@Slf4j
@Service
@Profile("springDataJPA")
public class ThresholdSDjpaService implements ThresholdService {

    private final ThresholdRepository thresholdRepository;

    public ThresholdSDjpaService(ThresholdRepository thresholdRepository) {
        this.thresholdRepository = thresholdRepository;
    }

    @Override
    public Threshold findByNumericalBoundary(int numericalBoundary) {
        return thresholdRepository.findByNumerical(numericalBoundary);
    }

    @Override
    public Threshold findByLetterGrade(String letterGrade) {
        return thresholdRepository.findByAlphabetical(letterGrade);
    }

    @Override
    public Threshold save(Threshold object) {
        return thresholdRepository.save(object);
    }

    @Override
    public Threshold findById(Long aLong) {
        return thresholdRepository.findById(aLong).orElse(null);
    }

    @Override
    public Set<Threshold> findAll() {
        Set<Threshold> thresholds = new HashSet<>();
        thresholdRepository.findAll().forEach(thresholds::add);
        return thresholds;
    }

    @Override
    public void delete(Threshold objectT) {
        thresholdRepository.delete(objectT);
    }

    @Override
    public void deleteById(Long aLong) {
        thresholdRepository.deleteById(aLong);
    }
}
