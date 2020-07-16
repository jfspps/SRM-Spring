package com.srm.model.people;

import com.srm.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;


@Setter
@Getter
@MappedSuperclass
public class StudentList extends BaseEntity {

    private String groupName;
}
