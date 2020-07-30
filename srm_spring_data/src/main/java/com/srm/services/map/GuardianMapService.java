package com.srm.services.map;

import com.srm.model.people.Guardian;
import com.srm.model.people.Teacher;
import com.srm.services.peopleServices.GuardianService;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//instruct Spring to inject the CRUD GuardianService into the application context as a bean
//note that neither AbstractService nor any of the services (GuardianService) is declared with @Service; the wiring is
//done through the map service
@Service
@NoArgsConstructor
//this service-map is also the default
@Profile(value = {"default", "map"})
public class GuardianMapService extends AbstractMapService<Guardian, Long> implements GuardianService {

    //map service which links the BaseService CRUD ops (via GuardianService) with AbstractMapService
    //generally, take a Guardian object from MapService and return AbstractMapService's method (hence super)

    @Override
    public Guardian save(Guardian guardian) {
        if (guardian != null) {
            return super.save(guardian);
        } else {
            System.out.println("Empty object passed to Guardian()");
            return null;
        }
    }

    @Override
    public Guardian findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Set<Guardian> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(Guardian guardian) {
        super.delete(guardian);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    //unique to the Guardian interface
    @Override
    public Guardian findByLastName(String lastName) {
        return this.findAll()
                .stream()
                .filter(guardian -> guardian.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Guardian findByFirstAndLastName(String firstName, String lastName) {
        return this.findAll()
                .stream()
                .filter(guardian -> guardian.getLastName().equalsIgnoreCase(lastName))
                .filter(guardian -> guardian.getFirstName().equalsIgnoreCase(firstName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Guardian> findAllByLastNameLike(String lastName) {
        return this.findAll()
                .stream()
                .filter(guardian -> guardian.getLastName().toLowerCase().contains(lastName.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Guardian> findAllByFirstNameLikeAndLastNameLike(String firstName, String lastName) {
        return this.findAll()
                .stream()
                .filter(guardian -> guardian.getFirstName().toLowerCase().contains(firstName.toLowerCase()))
                .filter(guardian -> guardian.getLastName().toLowerCase().contains(lastName.toLowerCase()))
                .collect(Collectors.toList());
    }
}
