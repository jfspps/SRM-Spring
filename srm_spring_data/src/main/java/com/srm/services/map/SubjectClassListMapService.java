package com.srm.services.map;


import com.srm.model.people.SubjectClassList;
import com.srm.services.peopleServices.SubjectClassListService;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@NoArgsConstructor
@Profile("map")
public class SubjectClassListMapService extends AbstractMapService<SubjectClassList, Long>
        implements SubjectClassListService {

    @Override
    public SubjectClassList save(SubjectClassList subjectClassList) {
        if (subjectClassList != null) {
            return super.save(subjectClassList);
        } else {
            System.out.println("Empty object passed to SubjectClassList()");
            return null;
        }
    }

    @Override
    public SubjectClassList findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Set<SubjectClassList> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(SubjectClassList objectT) {
        super.delete(objectT);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public SubjectClassList findBySubject(String subjectName) {
        return this.findAll()
                .stream()
                .filter(subjectClassList -> subjectClassList.getSubject().getSubjectName().equalsIgnoreCase(subjectName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public SubjectClassList findByTeacherLastName(String teacherLastName) {
        return this.findAll()
                .stream()
                .filter(subjectClassList -> subjectClassList.getTeacher().getLastName().equalsIgnoreCase(teacherLastName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public SubjectClassList findByGroupName(String groupName) {
        return this.findAll()
                .stream()
                .filter(subjectClassList -> subjectClassList.getGroupName().equalsIgnoreCase(groupName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public SubjectClassList findByTeacherFirstAndLastName(String firstName, String lastName) {
        return this.findAll()
                .stream()
                .filter(subjectClassList -> subjectClassList.getTeacher().getLastName().equalsIgnoreCase(lastName))
                .filter(subjectClassList -> subjectClassList.getTeacher().getFirstName().equalsIgnoreCase(firstName))
                .findFirst()
                .orElse(null);
    }
}
