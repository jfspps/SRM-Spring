package com.srm.model.people;

import com.srm.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {

    @Column(name = "first_line")
    private String firstLine;

    @Column(name = "second_line")
    private String secondLine;

    @Column(name = "postcode")
    private String postcode;

}
