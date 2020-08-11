package com.srm.converters;

import com.srm.model.academic.Subject;
import com.srm.model.people.Teacher;
import com.srm.properties.SubjectProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubjectPropertiesToSubjectTest {

    private static final String subjectName = "Physical Education";
    private static final List<Teacher> teacherList = new ArrayList<>();
    private static final Teacher teacher1 = new Teacher();
    private static final Teacher teacher2 = new Teacher();

    SubjectPropertiesToSubject converter;

    @BeforeEach
    void setUp() {
        converter = new SubjectPropertiesToSubject();
    }

    @Test
    void testNull(){
        assertNull(converter.convert(null));
    }

    @Test
    void testNotNull(){
        assertNotNull(converter.convert(new SubjectProperties()));
    }

    @Test
    void convert() {
        teacherList.add(teacher1);
        teacherList.add(teacher2);
        SubjectProperties subjectProperties = new SubjectProperties();
        subjectProperties.setSubjectName(subjectName);
        subjectProperties.setTeacherList(teacherList);

        Subject newSubject = converter.convert(subjectProperties);

        assertNotNull(newSubject);
        assertEquals(subjectName, newSubject.getSubjectName());
        assertEquals(teacherList.size(), newSubject.getTeachers().size());
    }
}