package com.srm.services.map;

import com.srm.model.academic.Threshold;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ThresholdMapServiceTest {

    final int boundary1 = 85;
    final int boundary2 = 90;
    final String letterGrade1 = "b";
    final String letterGrade2 = "DISTINCTION";

    Threshold thresholdA = Threshold.builder().id(1L).alphabetical(letterGrade1).numerical(boundary1).build();
    Threshold thresholdB = Threshold.builder().id(2L).alphabetical(letterGrade2).numerical(boundary2).build();

    ThresholdMapService thresholdMapService;

    @BeforeEach
    void setUp() {
        thresholdMapService = new ThresholdMapService();
        thresholdMapService.save(thresholdA);
        thresholdMapService.save(thresholdB);
    }

    @Test
    void findByNumericalBoundary() {
        Threshold found = thresholdMapService.findByNumericalBoundary(boundary1);
        assertEquals(boundary1, found.getNumerical());
    }

    @Test
    void findByLetterGradeLower() {
        Threshold found1 = thresholdMapService.findByLetterGrade(letterGrade1);
        assertEquals(letterGrade1, found1.getAlphabetical());
    }

    //verify that B != b
    @Test
    void findByLetterGradeLowerFalse() {
        Threshold found1 = thresholdMapService.findByLetterGrade("B");
        assertNull(found1);
    }

    @Test
    void findByLetterGradeUpper() {
        Threshold found2 = thresholdMapService.findByLetterGrade(letterGrade2);
        assertEquals(letterGrade2, found2.getAlphabetical());
    }

    //verify that "DISTINCTION" != "Distinction"
    @Test
    void findByLetterGradeUpperFalse() {
        Threshold found2 = thresholdMapService.findByLetterGrade("distinction");
        assertNull(found2);
    }
}