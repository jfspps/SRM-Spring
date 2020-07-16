package com.srm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

//directs JPA to inherit from this class though BaseEntity is not instantiated as a DB entity itself
//other base classes such as Person and StudentList are also annotated with @MappedSuperClass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {

    //this class defines the DB id methods for all other entities (people and academic)

    //this class and all subclasses are Serializable, whereby the object is converted to/from a byte stream

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
