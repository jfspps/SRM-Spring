package com.srm.services.map;

import com.srm.model.academic.ThresholdList;
import com.srm.services.academicServices.ThresholdListService;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@NoArgsConstructor
@Profile("map")
public class ThresholdListMapService extends AbstractMapService<ThresholdList, Long> implements ThresholdListService {

    @Override
    public ThresholdList save(ThresholdList thresholdList) {
        if (thresholdList != null) {
            return super.save(thresholdList);
        } else {
            System.out.println("Empty object passed to ThresholdList()");
            return null;
        }
    }

    @Override
    public ThresholdList findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Set<ThresholdList> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(ThresholdList objectT) {
        super.delete(objectT);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}
