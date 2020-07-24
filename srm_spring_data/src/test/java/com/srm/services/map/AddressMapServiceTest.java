package com.srm.services.map;

import com.srm.model.people.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AddressMapServiceTest {

    AddressMapService addressMapService;

    Address address;
    final String firstLine = "28 North Street";
    final String secondLine = "SomeCity";
    final String postcode = "ABCD1234";
    final Long id = 1L;

    @BeforeEach
    void setUp() {
        addressMapService = new AddressMapService();
        address = Address.builder()
                .firstLine(firstLine)
                .secondLine(secondLine)
                .postcode(postcode).build();
    }

    @Test
    void saveWithoutID() {
        Address savedAddress = addressMapService.save(address);
        assertEquals(firstLine, savedAddress.getFirstLine());
        assertEquals(secondLine, savedAddress.getSecondLine());
        assertEquals(postcode, savedAddress.getPostcode());
    }

    @Test
    void saveWithId() {
        Address savedAddress = Address.builder().id(id).build();
        addressMapService.save(savedAddress);
        assertEquals(id, savedAddress.getId());
    }

    @Test
    void findById() {
        address.setId(id);
        addressMapService.save(address);
        Address anotherAddress = addressMapService.findById(id);
        assertNotNull(anotherAddress);
        assertEquals(id, anotherAddress.getId());
    }

    @Test
    void findByIdFalse() {
        Address foundAddress = addressMapService.findById(2L);
        assertNull(foundAddress);
    }

    @Test
    void findAll() {
        Set<Address> addresses = new HashSet<>();
        addresses.add(addressMapService.save(address));
        assertEquals(1, addresses.size());
    }

    @Test
    void delete() {
        Address savedAddress = Address.builder().id(id).build();
        addressMapService.save(savedAddress);
        assertEquals(id, savedAddress.getId());
        addressMapService.delete(savedAddress);
        assertEquals(0, addressMapService.findAll().size());
    }

    @Test
    void deleteById() {
        Address savedAddress = Address.builder().id(id).build();
        addressMapService.save(savedAddress);
        assertEquals(id, savedAddress.getId());
        addressMapService.deleteById(id);
        assertEquals(0, addressMapService.findAll().size());
    }

    @Test
    void findByPostCode() {
        addressMapService.save(address);
        Address retrievedAddress = addressMapService.findByPostCode(postcode);
        assertEquals(postcode, retrievedAddress.getPostcode());
    }
}