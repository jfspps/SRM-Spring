package com.srm.model.academic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.srm.model.BaseEntity;
import com.srm.model.people.Teacher;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "subjects")
public class Subject extends BaseEntity {

    @Builder
    public Subject(Long id, String subjectName, Set<Teacher> teachers) {
        super(id);
        this.subjectName = subjectName;
        if (teachers != null){
            this.teachers = teachers;
        }
    }

    @Size(min = 2, max = 255)
    @NotBlank
    @Column(name = "subject_name")
    private String subjectName;

    //"subjects" refers to Teacher.subjects local variable (Set)
    @ManyToMany(mappedBy = "subjects")
    //the @JsonIgnore added to prevent Spring from creating infinitely long JSONs
    //(https://stackoverflow.com/questions/20813496/tomcat-exception-cannot-call-senderror-after-the-response-has-been-committed)
    @JsonIgnore
    private Set<Teacher> teachers = new HashSet<>();
}
