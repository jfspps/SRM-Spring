package com.srm.model.services.map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractMapService<T, ID> {

    //this class provides a map-based (Python dictionary) service (an alternative to JPA and JDBC models)
    //maps are located by a key (in this case ID) and contain a value (in this case T)

    protected Map<ID, T> map = new HashMap<>();

    T save(ID id, T object) {
        map.put(id, object);
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
}
