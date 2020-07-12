package com.srm.services;

import java.util.Set;

public interface CrudService<T, ID> {

    //generic interface which declares common CRUD operations on type parameters T and ID
    //this interface mimics some of the methods from CrudRepository interface from Spring Data

    T save(T object);

    T findById(ID id);

    Set<T> findAll();

    void delete(T objectT);

    void deleteById(ID id);
}
