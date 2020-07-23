package com.srm.services.springDataJPA;

import com.srm.model.people.Address;
import com.srm.model.people.Guardian;
import com.srm.model.people.Student;
import com.srm.repositories.GuardianRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//see StudentSDjpaServiceTest for commentary
@ExtendWith(MockitoExtension.class)
class GuardianSDjpaServiceTest {

    @Mock
    GuardianRepository guardianRepository;

    final Long id = 1L;
    final String firstName = "Jimmy";
    final String lastName = "White";

    //check that Address checks out (at this point, seems unlikely to have a page dedicated to all addresses)
    final Address address = Address.builder()
            .firstLine("28 North Street")
            .secondLine("SomeCity")
            .postcode("ABCD1234").build();
    final Student student1 = new Student();
    final Student student2 = new Student();
    Set<Student> studentSet = new HashSet<>();

    Guardian mockGuardian;

    @InjectMocks
    GuardianSDjpaService guardianSDjpaService;

    @BeforeEach
    void setUp() {
        studentSet.add(student1);
        studentSet.add(student2);
        mockGuardian = Guardian.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .address(address)
                .students(studentSet)
                .build();
    }

    @Test
    void findByLastName() {
        when(guardianRepository.findByLastName(any())).thenReturn(mockGuardian);

        Guardian testGuardian = guardianSDjpaService.findByLastName("otherName");
        assertNotNull(testGuardian);
        assertEquals(lastName, testGuardian.getLastName());
        verify(guardianRepository).findByLastName(any());
    }

    @Test
    void saveWithId() {
        when(guardianRepository.save(any())).thenReturn(mockGuardian);

        Guardian savedGuardian = guardianSDjpaService.save(mockGuardian);
        assertEquals(id, savedGuardian.getId());
        verify(guardianRepository, times(1)).save(any());
    }

    @Test
    void saveWithoutId() {
        Guardian noIdeaGuardian = Guardian.builder().firstName("The").lastName("Someone").build();
        when(guardianRepository.save(any())).thenReturn(noIdeaGuardian);

        assertNotNull(noIdeaGuardian);
        Guardian savedGuardian = guardianSDjpaService.save(noIdeaGuardian);
        verify(guardianRepository, times(1)).save(any());
        System.out.println("Guardian with no ID as ID: " + savedGuardian.getId());  //JPA seems to accept null IDs...
    }

    @Test
    void findById() {
        when(guardianRepository.findById(any())).thenReturn(Optional.of(mockGuardian));

        Guardian guardianFound = guardianSDjpaService.findById(4L);
        assertEquals(id, guardianFound.getId());
        verify(guardianRepository, times(1)).findById(anyLong());
    }

    @Test
    void findAddress() {
        when(guardianRepository.findById(any())).thenReturn(Optional.of(mockGuardian));

        Address addressFound = guardianSDjpaService.findById(id).getAddress();
        assertEquals(address.getFirstLine(), addressFound.getFirstLine());
    }

    @Test
    void findAll() {
        Guardian guardian1 = Guardian.builder().build();
        Guardian guardian2 = Guardian.builder().build();
        Set<Guardian> guardians = new HashSet<>();
        guardians.add(guardian1);
        guardians.add(guardian2);

        when(guardianRepository.findAll()).thenReturn(guardians);
        Set<Guardian> returnedGuardians = guardianSDjpaService.findAll();
        assertNotNull(returnedGuardians);
        assertEquals(2, returnedGuardians.size());
    }

    @Test
    void delete() {
        // see StudentSDjpaServiceTest
        assertEquals(0, guardianSDjpaService.findAll().size());
        guardianSDjpaService.delete(mockGuardian);
        assertEquals(0, guardianSDjpaService.findAll().size());
    }

    @Test
    void deleteById() {
        // see StudentSDjpaServiceTest
        assertEquals(0, guardianSDjpaService.findAll().size());
        guardianSDjpaService.deleteById(id);
        assertEquals(0, guardianSDjpaService.findAll().size());
    }
}