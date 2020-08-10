package com.srm.services.map;

import com.srm.exceptions.NotFoundException;
import com.srm.model.people.Address;
import com.srm.model.people.Guardian;
import com.srm.model.people.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GuardianMapServiceTest {

    GuardianMapService guardianMapService;
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

    @BeforeEach
    void setUp() {
        guardianMapService = new GuardianMapService();
        studentSet.add(student1);
        studentSet.add(student2);
        guardianMapService.save(Guardian.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .address(address)
                .students(studentSet).build());
    }

    @Test
    void saveWithId() {
        Guardian guardian_withID = Guardian.builder().id(id).build();
        assertNotNull(guardian_withID);

        Guardian savedGuardian = guardianMapService.save(guardian_withID);
        assertNotNull(savedGuardian);
        assertEquals(id, savedGuardian.getId());
        System.out.println("Guardian with ID: " + savedGuardian.getId());
        assertEquals(1, guardianMapService.findAll().size());
    }

    @Test
    void saveWithoutId() {
        Guardian guardian_withoutID = Guardian.builder().build();
        guardianMapService.save(guardian_withoutID);
        assertNotNull(guardian_withoutID);
        assertNotNull(guardian_withoutID.getId());
        assertEquals(2, guardianMapService.findAll().size());
        System.out.println("Guardian without ID has ID: " + guardian_withoutID.getId());
    }

    @Test
    void guardianAddress() {
        Guardian guardianWithAddress = guardianMapService.findById(id);     //verified below
        assertEquals(address.getFirstLine(), guardianWithAddress.getAddress().getFirstLine());
        assertEquals(address.getSecondLine(), guardianWithAddress.getAddress().getSecondLine());
        assertEquals(address.getPostcode(), guardianWithAddress.getAddress().getPostcode());
    }

    @Test
    void findById() {
        Guardian guardianWithAddress = guardianMapService.findById(id);
        assertEquals(id, guardianWithAddress.getId());
    }

    @Test
    void notFoundGuardianByIdTest(){
        Throwable exception = assertThrows(NotFoundException.class, () -> {
            guardianMapService.findById(8L);
        });

        assertEquals("Guardian not found", exception.getMessage());
    }

    @Test
    void findAll() {
        Set<Guardian> guardians = guardianMapService.findAll();
        assertEquals(1, guardians.size());
    }

    @Test
    void delete() {
        guardianMapService.delete(guardianMapService.findAll().iterator().next());
        assertEquals(0, guardianMapService.findAll().size());
    }

    @Test
    void deleteById() {
        guardianMapService.deleteById(id);
        assertEquals(0, guardianMapService.findAll().size());
    }

    @Test
    void findByLastName() {
        Guardian guardianWithName = guardianMapService.findByLastName(lastName);
        assertEquals(lastName, guardianWithName.getLastName());
    }

    @Test
    void findByFirstAndLastName() {
        Guardian guardianFullName = guardianMapService.findByFirstAndLastName(firstName, lastName);
        assertEquals(lastName, guardianFullName.getLastName());
        assertEquals(firstName, guardianFullName.getFirstName());
    }
}