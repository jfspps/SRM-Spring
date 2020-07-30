package com.srm.repositories.peopleRepos;

import com.srm.model.people.Student;
import com.srm.model.people.Teacher;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//the implementation of the following methods is supplied automatically by JPA

//declares additional SpringDataJPA CRUD functionality for Teachers
public interface TeacherRepository extends CrudRepository<Teacher, Long> {

    Teacher findByLastName(String lastName);

    Teacher findByFirstNameAndLastName(String firstName, String lastName);

    List<Teacher> findAllByLastNameLike(String lastName);

    List<Teacher> findAllByFirstNameLikeAndLastNameLike(String firstName, String lastName);
}
