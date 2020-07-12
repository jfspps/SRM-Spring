package com.srm.model.repositories;

import com.srm.model.people.Student;
import org.springframework.data.repository.CrudRepository;

//provide CRUD functionality to Students
public interface StudentRepository extends CrudRepository<Student, Long> {
}
