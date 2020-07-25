package com.srm.services.springDataJPA;

import com.srm.model.people.ContactDetail;
import com.srm.repositories.peopleRepos.ContactDetailRepository;
import com.srm.services.peopleServices.ContactDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Profile("springDataJPA")
public class ContactDetailSDjpaService implements ContactDetailService {

    private final ContactDetailRepository contactDetailRepository;

    public ContactDetailSDjpaService(ContactDetailRepository contactDetailRepository) {
        this.contactDetailRepository = contactDetailRepository;
    }

    @Override
    public ContactDetail findByEmail(String email) {
        return contactDetailRepository.findByEmail(email);
    }

    @Override
    public ContactDetail findByPhone(String phoneNumber) {
        return contactDetailRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public ContactDetail save(ContactDetail object) {
        return contactDetailRepository.save(object);
    }

    @Override
    public ContactDetail findById(Long aLong) {
        return contactDetailRepository.findById(aLong).orElse(null);
    }

    @Override
    public Set<ContactDetail> findAll() {
        Set<ContactDetail> contactDetails = new HashSet<>();
        contactDetailRepository.findAll().forEach(contactDetails::add);
        return contactDetails;
    }

    @Override
    public void delete(ContactDetail objectT) {
        contactDetailRepository.delete(objectT);
    }

    @Override
    public void deleteById(Long aLong) {
        contactDetailRepository.deleteById(aLong);
    }
}
