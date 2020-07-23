package com.srm.services.springDataJPA;

import com.srm.model.people.Guardian;
import com.srm.repositories.peopleRepos.GuardianRepository;
import com.srm.services.peopleServices.GuardianService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

//see StudentSDjpaService for commentary
@Slf4j
@Service
@Profile("springDataJPA")
public class GuardianSDjpaService implements GuardianService {

    private final GuardianRepository guardianRepository;

    public GuardianSDjpaService(GuardianRepository guardianRepository) {
        this.guardianRepository = guardianRepository;
    }

    @Override
    public Guardian findByLastName(String lastName) {
        return guardianRepository.findByLastName(lastName);
    }

    @Override
    public Guardian findByFirstAndLastName(String firstName, String lastName) {
        return guardianRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Guardian save(Guardian object) {
        return guardianRepository.save(object);
    }

    @Override
    public Guardian findById(Long aLong) {
        return guardianRepository.findById(aLong).orElse(null);
    }

    @Override
    public Set<Guardian> findAll() {
        Set<Guardian> guardians = new HashSet<>();
        guardianRepository.findAll().forEach(guardians::add);
        return guardians;
    }

    @Override
    public void delete(Guardian objectT) {
        guardianRepository.delete(objectT);
    }

    @Override
    public void deleteById(Long aLong) {
        guardianRepository.deleteById(aLong);
    }

}
