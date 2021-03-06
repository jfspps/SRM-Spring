package com.srm.services.map;

import com.srm.model.BaseEntity;

import java.util.*;

//set generic types T and ID to extend known classes

public abstract class AbstractMapService<T extends BaseEntity, ID extends Long> {

    // this class provides a map-based (Python dictionary) service (an alternative to JPA and JDBC models)
    // maps are located by a key (in this case ID) and contain a value (in this case T)
    // "map" stores everything

    protected Map<Long, T> map = new HashMap<>();

    // the following save(), findById(), findAll(), delete() and deleteById() are only accessible to classes in
    // this package (com.arm.services.map) with accessible terms between protected and private (hence all subclasses
    // of AbstractMapService are currently in the same package)

    T save(T object) {
        if (object != null) {
            if (object.getId() == null) {
                object.setId(getNextId());
            }
            map.put(object.getId(), object);
        } else {
            throw new RuntimeException("Object cannot be null");
        }

        return object;
    }

    T findById(ID id) {
        return map.get(id);
    }

    Set<T> findAll() {
        //hand over all values of the map HashMap
        return new HashSet<>(map.values());
    }

    void delete(T objectT) {
        //get a Set view of map and remove all entries which match objectT
        map.entrySet().removeIf(entry -> entry.getValue().equals(objectT));
    }

    void deleteById(ID id) {
        map.remove(id);
    }

    //get Spring to generate an ID starting from 1 if the map is empty or 1+ current ID if map is allocated
    private Long getNextId() {
        if (map.isEmpty())
            return 1L;
        else
            //identify the current highest key and use it to assign the next
            return Collections.max(map.keySet()) + 1;
    }
}
