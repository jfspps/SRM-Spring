package com.srm.converters;

import com.srm.model.academic.Subject;
import com.srm.properties.SubjectProperties;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class SubjectPropertiesToSubject implements Converter<SubjectProperties, Subject> {

    @Synchronized
    @Nullable
    @Override
    public Subject convert(SubjectProperties subjectProperties) {

        if (subjectProperties == null){
            return null;
        }

        final Subject subject = new Subject();

        subject.setSubjectName(subjectProperties.getSubjectName());
        if (subjectProperties.getTeacherList().isEmpty()){
            subject.setTeachers(new HashSet<>());
        } else {
            subject.setTeachers(new HashSet<>(subjectProperties.getTeacherList()));
        }

        return subject;
    }
}
