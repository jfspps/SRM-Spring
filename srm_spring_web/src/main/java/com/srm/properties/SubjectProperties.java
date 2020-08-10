package com.srm.properties;

import com.srm.model.people.Teacher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SubjectProperties {

    //collect all properties for Subject and set required validations
    //this class filters and validates user entered data, and then converted (see /converters)

    @NotBlank
    @Size(min = 2, max = 255)
    private String subjectName;

    private List<Teacher> teacherList = new ArrayList<>();
}
