package com.srm.model.people;

import com.srm.model.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "contact_detail")
public class ContactDetail extends BaseEntity {

    @Builder
    public ContactDetail(Long id, String email, String phoneNumber) {
        super(id);
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;
}
