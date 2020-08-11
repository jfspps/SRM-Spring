package com.srm.converters;

import com.srm.model.academic.Subject;
import com.srm.model.people.Teacher;
import com.srm.properties.SubjectProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SubjectToSubjectPropertiesTest {

    private static final String subjectName = "Physical Education";
    private static final Set<Teacher> teacherList = new HashSet<>();
    private static final Teacher teacher1 = new Teacher();
    private static final Teacher teacher2 = new Teacher();

    SubjectToSubjectProperties converter;

    @BeforeEach
    void setUp() {
        converter = new SubjectToSubjectProperties();
    }

    @Test
    void testNull(){
        assertNull(converter.convert(null));
    }

    @Test
    void testNotNull(){
        assertNotNull(converter.convert(new Subject()));
    }

    @Test
    void convert() {
        teacherList.add(teacher1);
        teacherList.add(teacher2);
        Subject subject = new Subject();
        subject.setSubjectName(subjectName);
        subject.setTeachers(teacherList);

        SubjectProperties newSubjectProp = converter.convert(subject);

        assertNotNull(newSubjectProp);
        assertEquals(subjectName, newSubjectProp.getSubjectName());
        assertEquals(teacherList.size(), newSubjectProp.getTeacherList().size());
    }
}