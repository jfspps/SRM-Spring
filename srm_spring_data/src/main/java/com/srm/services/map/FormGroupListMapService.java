package com.srm.services.map;

import com.srm.model.people.FormGroupList;
import com.srm.services.peopleServices.FormGroupListService;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@NoArgsConstructor
@Profile("map")
public class FormGroupListMapService extends AbstractMapService<FormGroupList, Long> implements FormGroupListService {

    @Override
    public FormGroupList save(FormGroupList formGroupList) {
        if (formGroupList != null) {
            return super.save(formGroupList);
        } else {
            System.out.println("Empty object passed to FormGroupList()");
            return null;
        }
    }

    @Override
    public FormGroupList findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Set<FormGroupList> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(FormGroupList objectT) {
        super.delete(objectT);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public FormGroupList findByGroupName(String groupName) {
        return this.findAll()
                .stream()
                .filter(groupList -> groupList.getGroupName().equalsIgnoreCase(groupName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public FormGroupList findByTeacherLastName(String teacherLastName) {
        return this.findAll()
                .stream()
                .filter(groupList -> groupList.getTeacher().getLastName().equalsIgnoreCase(teacherLastName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public FormGroupList findByTeacherFirstAndLastName(String firstName, String lastName) {
        return this.findAll()
                .stream()
                .filter(formGroupList -> formGroupList.getTeacher().getLastName().equalsIgnoreCase(lastName))
                .filter(formGroupList -> formGroupList.getTeacher().getFirstName().equalsIgnoreCase(firstName))
                .findFirst()
                .orElse(null);
    }
}
