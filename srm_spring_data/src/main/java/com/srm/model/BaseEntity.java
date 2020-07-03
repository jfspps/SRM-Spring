package com.srm.model;

import java.io.Serializable;

public class BaseEntity implements Serializable {

    //this class defines the DB id methods for all other entities (people and academic)

    //this class and all subclasses are Serializable, whereby the object is converted to/from a byte stream

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
