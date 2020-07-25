package com.srm.services;

import java.util.Set;

public interface CrudService<T, ID> {

    // generic interface which declares common CRUD operations on type parameters T and ID
    // this interface mimics some of the methods from CrudRepository interface from Spring Data

    // CrudService and all other interfaces which extend it are "injection agnostic"
    // In SRM there are two implementations of CrudService which defines the CRUD methods:
    // 1. HashSet based mapping, found under com.srm.services.map (see AbstractMapService)
    // 2. Spring Data JPA mapping, found under /services/springDataJPA (methods defined by JPA as Repositories,
    // located in com.srm.repositories)

    // The basis for the services is provided by com.srm.model (see BaseEntity)

    T save(T object);

    T findById(ID id);

    Set<T> findAll();

    void delete(T objectT);

    void deleteById(ID id);
}
