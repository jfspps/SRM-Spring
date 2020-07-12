package com.srm.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

//directs JPA to inherit from this class though BaseEntity is not instantiated as a DB entity itself
//other base classes such as Person and StudentList are also annotated with @MappedSuperClass
@MappedSuperclass
public class BaseEntity implements Serializable {

    //this class defines the DB id methods for all other entities (people and academic)

    //this class and all subclasses are Serializable, whereby the object is converted to/from a byte stream

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
