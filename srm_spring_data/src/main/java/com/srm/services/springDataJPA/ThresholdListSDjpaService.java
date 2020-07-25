package com.srm.services.springDataJPA;

import com.srm.model.academic.ThresholdList;
import com.srm.repositories.academicRepos.ThresholdListRepository;
import com.srm.services.academicServices.ThresholdListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

//see StudentSDjpaService for commentary
@Slf4j
@Service
@Profile("springDataJPA")
public class ThresholdListSDjpaService implements ThresholdListService {

    private final ThresholdListRepository thresholdListRepository;

    public ThresholdListSDjpaService(ThresholdListRepository thresholdListRepository) {
        this.thresholdListRepository = thresholdListRepository;
    }

    @Override
    public ThresholdList save(ThresholdList thresholdList) {
        return thresholdListRepository.save(thresholdList);
    }

    @Override
    public ThresholdList findById(Long aLong) {
        return thresholdListRepository.findById(aLong).orElse(null);
    }

    @Override
    public Set<ThresholdList> findAll() {
        Set<ThresholdList> thresholdLists = new HashSet<>();
        thresholdListRepository.findAll().forEach(thresholdLists::add);
        return thresholdLists;
    }

    @Override
    public void delete(ThresholdList objectT) {
        thresholdListRepository.delete(objectT);
    }

    @Override
    public void deleteById(Long aLong) {
        thresholdListRepository.deleteById(aLong);
    }
}
