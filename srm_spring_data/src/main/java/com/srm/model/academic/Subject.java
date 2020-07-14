package com.srm.model.academic;

import com.srm.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "subjects")
public class Subject extends BaseEntity {

    //future: if there is a need to query which teachers teach a given subject then this POJO may need Set<Teacher>

    @Column(name = "subject_name")
    private String subjectName;
}
