package com.srm.model.people;

import com.srm.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;


@Setter
@Getter
@MappedSuperclass
public class StudentList extends BaseEntity {

    @Column(name = "group_name")
    private String groupName;

}
