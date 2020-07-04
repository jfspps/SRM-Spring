package com.srm.model.services.map;

import com.srm.model.people.Guardian;
import com.srm.model.services.BaseService;

import java.util.Set;

public class GuardianServiceMap extends AbstractMapService<Guardian, Long> implements BaseService<Guardian, Long> {

    //map service which links the BaseService CRUD ops with AbstractMapService specifically for Guardians
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
}
