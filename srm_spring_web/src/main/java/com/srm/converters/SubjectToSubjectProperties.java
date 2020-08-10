package com.srm.converters;

import com.srm.model.academic.Subject;
import com.srm.properties.SubjectProperties;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SubjectToSubjectProperties implements Converter<Subject, SubjectProperties> {

    @Synchronized
    @Nullable
    @Override
    public SubjectProperties convert(Subject subject) {

        if (subject == null){
            return null;
        }

        final SubjectProperties subjectProperties = new SubjectProperties();

        subjectProperties.setSubjectName(subject.getSubjectName());
        if (subject.getTeachers().isEmpty()){
            subjectProperties.setTeacherList(new ArrayList<>());
        } else {
            subjectProperties.setTeacherList(new ArrayList<>(subject.getTeachers()));
        }

        return subjectProperties;
    }
}
