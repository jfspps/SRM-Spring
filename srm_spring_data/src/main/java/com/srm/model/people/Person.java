package com.srm.model.people;

import com.srm.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Person extends BaseEntity {

    //@NotBlank for form validation
    @NotBlank
    @Size(min = 1, max = 255)
    //Hibernate uses snake case by default so the name argument is somewhat redundant here
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 255)
    @Column(name = "last_name")
    private String lastName;

    //transferred to Student, Teacher and Guardian??? wait and see
    @OneToOne
    private ContactDetail contactDetail;

    public Person(Long id, String firstName, String lastName, ContactDetail contactDetail) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactDetail = contactDetail;
    }
}
