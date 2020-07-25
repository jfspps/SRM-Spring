package com.srm.services.springDataJPA;

import com.srm.model.academic.Threshold;
import com.srm.repositories.academicRepos.ThresholdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

//see StudentSDjpaServiceTest for commentary
@ExtendWith(MockitoExtension.class)
class ThresholdSDjpaServiceTest {

    final int boundary1 = 85;
    final int boundary2 = 90;
    final String letterGrade1 = "b";
    final String letterGrade2 = "DISTINCTION";

    Threshold thresholdA = Threshold.builder().id(1L).alphabetical(letterGrade1).numerical(boundary1).build();
    Threshold thresholdB = Threshold.builder().id(2L).alphabetical(letterGrade2).numerical(boundary2).build();

    @Mock
    ThresholdRepository thresholdRepository;

    @InjectMocks
    ThresholdSDjpaService thresholdSDjpaService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findByNumericalBoundary() {
        when(thresholdRepository.findByNumerical(anyInt())).thenReturn(thresholdA);

        Threshold found = thresholdSDjpaService.findByNumericalBoundary(354);
        verify(thresholdRepository, times(1)).findByNumerical(anyInt());
        assertEquals(boundary1, found.getNumerical());
    }

    @Test
    void findByLetterGrade() {
        when(thresholdRepository.findByAlphabetical(anyString())).thenReturn(thresholdB);

        Threshold found = thresholdSDjpaService.findByLetterGrade("something");
        verify(thresholdRepository, times(1)).findByAlphabetical(anyString());
        assertEquals(letterGrade2, found.getAlphabetical());
    }

    @Test
    void findByLetterGradeWrongCase() {
        when(thresholdRepository.findByAlphabetical(anyString())).thenReturn(thresholdA);

        Threshold found = thresholdSDjpaService.findByLetterGrade("something");
        verify(thresholdRepository, times(1)).findByAlphabetical(anyString());
        assertNotEquals("B", found.getAlphabetical());
    }
}