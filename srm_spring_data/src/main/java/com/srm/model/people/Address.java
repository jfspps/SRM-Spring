package com.srm.model.people;

import com.srm.model.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Address extends BaseEntity {
    //at present, there is no plan to implement a database table of addresses, rather, they are stored in Guardians
    @Builder
    public Address(Long id, String firstLine, String secondLine, String postcode) {
        super(id);
        this.firstLine = firstLine;
        this.secondLine = secondLine;
        this.postcode = postcode;
    }

    private String firstLine;

    private String secondLine;

    private String postcode;

}
