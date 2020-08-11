package com.srm.model.people;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "guardians")
public class Guardian extends Person {

    @Builder
    public Guardian(Long id, String firstName, String lastName, Address address, Set<Student> students, ContactDetail contactDetail) {
        super(id, firstName, lastName, contactDetail);
        this.address = address;
        if (students != null){
            this.students = students;
        }
    }

    //the @JsonIgnore added to prevent Spring from creating infinitely long JSONs
    //(https://stackoverflow.com/questions/20813496/tomcat-exception-cannot-call-senderror-after-the-response-has-been-committed)
    @JsonIgnore
    @ManyToOne
    private Address address;

    //the @JsonIgnore added to prevent Spring from creating infinitely long JSONs
    //(https://stackoverflow.com/questions/20813496/tomcat-exception-cannot-call-senderror-after-the-response-has-been-committed)
    @JsonIgnore
    //"guardians" refers to the Set<Guardian> fro Student
    @ManyToMany(mappedBy = "guardians")
    private Set<Student> students = new HashSet<>();
}