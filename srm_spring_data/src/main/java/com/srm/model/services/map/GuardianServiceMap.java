package com.srm.model.services.map;

import com.srm.model.people.Guardian;
import com.srm.model.services.GuardianService;

import java.util.Set;

public class GuardianServiceMap extends AbstractMapService<Guardian, Long> implements GuardianService {

    //map service which links the BaseService CRUD ops (via GuardianService) with AbstractMapService
    //generally, take a Guardian object from MapService and return AbstractMapService's method (hence super)

    @Override
    public Guardian save(Guardian guardian) {
        return super.save(guardian.getId(), guardian);
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
    public Guardian findByName(String name) {
        return null;
    }
}
