package com.srm.services.map;

import com.srm.model.people.Guardian;
import com.srm.services.GuardianService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

//instruct Spring to inject the CRUD GuardianService into the application context as a bean
//note that neither AbstractService nor any of the services (GuardianService) is declared with @Service; the wiring is
//done through the map service
@Service
//this service-map is also the default
@Profile(value = {"default", "map"})
public class GuardianServiceMap extends AbstractMapService<Guardian, Long> implements GuardianService {

    //map service which links the BaseService CRUD ops (via GuardianService) with AbstractMapService
    //generally, take a Guardian object from MapService and return AbstractMapService's method (hence super)

    @Override
    public Guardian save(Guardian guardian) {
        return super.save(guardian);
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
}
