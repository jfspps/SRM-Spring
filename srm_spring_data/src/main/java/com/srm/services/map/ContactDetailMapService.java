package com.srm.services.map;

import com.srm.model.people.ContactDetail;
import com.srm.services.peopleServices.ContactDetailService;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@NoArgsConstructor
@Profile("map")
public class ContactDetailMapService extends AbstractMapService<ContactDetail, Long> implements ContactDetailService {

    @Override
    public ContactDetail save(ContactDetail contactDetail) {
        if (contactDetail != null) {
            return super.save(contactDetail);
        } else {
            System.out.println("Empty object passed to ContactDetail()");
            return null;
        }
    }

    @Override
    public ContactDetail findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Set<ContactDetail> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(ContactDetail objectT) {
        super.delete(objectT);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public ContactDetail findByEmail(String email) {
        return this.findAll()
                .stream()
                .filter(contactDetail -> contactDetail.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public ContactDetail findByPhone(String phoneNumber) {
        return this.findAll()
                .stream()
                .filter(contactDetail -> contactDetail.getPhoneNumber().equalsIgnoreCase(phoneNumber))
                .findFirst()
                .orElse(null);
    }
}
