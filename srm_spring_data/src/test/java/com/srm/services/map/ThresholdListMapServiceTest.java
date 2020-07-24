package com.srm.services.map;

import com.srm.model.academic.Threshold;
import com.srm.model.academic.ThresholdList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ThresholdListMapServiceTest {

    Threshold threshold1 = Threshold.builder().numerical(80).alphabetical("B").build();
    Threshold threshold2 = Threshold.builder().numerical(85).alphabetical("A").build();
    Set<Threshold> thresholds = new HashSet<>();
    ThresholdList thresholdList;
    final Long id = 1L;

    ThresholdListMapService thresholdListMapService;

    @BeforeEach
    void setUp() {
        thresholdListMapService = new ThresholdListMapService();
        thresholds.add(threshold1);
        thresholds.add(threshold2);
        thresholdList = ThresholdList.builder().thresholds(thresholds).id(id).build();
        thresholdListMapService = new ThresholdListMapService();
    }

    @Test
    void save() {
        ThresholdList savedList = thresholdListMapService.save(thresholdList);
        assertNotNull(savedList);
    }

    @Test
    void findById() {
        thresholdListMapService.save(thresholdList);
        ThresholdList found = thresholdListMapService.findById(id);
        assertEquals(id, found.getId());
    }

    @Test
    void findAll() {
        thresholdListMapService.save(thresholdList);
        Set<ThresholdList> thresholdLists = thresholdListMapService.findAll();
        assertEquals(1, thresholdLists.size());
    }

    @Test
    void delete() {
        ThresholdList savedList = thresholdListMapService.save(thresholdList);
        thresholdListMapService.delete(savedList);
        assertEquals(0, thresholdListMapService.findAll().size());
    }

    @Test
    void deleteById() {
        ThresholdList savedList = thresholdListMapService.save(thresholdList);
        thresholdListMapService.deleteById(id);
        assertEquals(0, thresholdListMapService.findAll().size());
    }
}