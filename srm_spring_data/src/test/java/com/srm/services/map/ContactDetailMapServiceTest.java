package com.srm.services.map;

import com.srm.model.people.ContactDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContactDetailMapServiceTest {

    ContactDetailMapService contactDetailMapService;
    ContactDetail contactDetail;

    String phoneNumber = "027432847328237";
    String emailAddress = "one@exmaple.com";

    @BeforeEach
    void setUp() {
        contactDetail = ContactDetail.builder().email(emailAddress).id(1L).phoneNumber(phoneNumber).build();
        contactDetailMapService = new ContactDetailMapService();
        contactDetailMapService.save(contactDetail);
    }

    @Test
    void findByEmail() {
        ContactDetail found1 = contactDetailMapService.findByEmail(emailAddress);
        assertEquals(emailAddress, found1.getEmail());
    }

    @Test
    void findByPhone() {
        ContactDetail found2 = contactDetailMapService.findByPhone(phoneNumber);
        assertEquals(phoneNumber, found2.getPhoneNumber());
    }
}