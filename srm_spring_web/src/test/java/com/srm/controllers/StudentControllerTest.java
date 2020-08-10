package com.srm.controllers;

import com.srm.exceptions.NotFoundException;
import com.srm.model.people.*;
import com.srm.services.peopleServices.GuardianService;
import com.srm.services.peopleServices.StudentService;
import com.srm.services.peopleServices.TeacherService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    StudentService studentService;

    @Mock
    TeacherService teacherService;

    @Mock
    GuardianService guardianService;

    @InjectMocks
    StudentController studentController;

    MockMvc mockMvc;
    //prepare DB entries
    Set<Student> studentSet_1;
    Set<Student> studentSet_2;

    Student testStudent;
    Teacher teacher1;
    Guardian guardian1;
    FormGroupList formGroupList1;
    ContactDetail teacher1Contact;
    Address address1;

    @BeforeEach
    void setUp() {
        studentSet_2 = new HashSet<>(); //size 2
        studentSet_1 = new HashSet<>(); //size 1
        studentSet_2.add(Student.builder().id(1L).build());
        studentSet_2.add(Student.builder().id(2L).build());
        studentSet_1.add(Student.builder().build());

        teacher1Contact = ContactDetail.builder().email("teacher1@school.com").phoneNumber("9847324").build();
        teacher1 = Teacher.builder().firstName("Keith").lastName("Thomson").contactDetail(teacher1Contact).build();
        address1 = Address.builder().firstLine("88 Penine Way").secondLine("Farnborough").postcode("CHG9475JF").build();
        guardian1 = Guardian.builder().firstName("Alan").lastName("Smith").address(address1).build();
        Set<Guardian> guardianGroup = new HashSet<>();
        guardianGroup.add(guardian1);
        Set<Student> studentgroup1 = new HashSet<>();
        formGroupList1 = FormGroupList.builder().studentList(studentgroup1).groupName("Group 1").teacher(teacher1).build();

        testStudent = Student.builder()
                .firstName("Elizabeth")
                .lastName("Jones")
                .contactDetail(teacher1Contact)
                .formGroupList(formGroupList1)
                .personalTutor(teacher1)
                .guardians(guardianGroup)
                .build();

        studentgroup1.add(testStudent);

        //provides each test method with a mock controller based on studentIndexController
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    void listStudents() throws Exception {
        //direct any call to findAll() to use above studentSet instead
        when(studentService.findAll()).thenReturn(studentSet_2);

        mockMvc.perform(get("/students"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("students/index"))
                .andExpect(model().attribute("students", hasSize(2)));  //two records in findAll() Set
    }

    @Test
    void listStudentsIndex() throws Exception {
        // direct any call to findAll() to use above studentSet instead
        // (there should be no calls to studentService, see below)
        when(studentService.findAll()).thenReturn(studentSet_2);

        mockMvc.perform(get("/students/index"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("students/index"))
                .andExpect(model().attribute("students", hasSize(2))); //two records in findAll() Set
    }

    @Test
    void listStudentsIndexHTML() throws Exception {
        //direct any call to findAll() to use above studentSet instead
        when(studentService.findAll()).thenReturn(studentSet_2);

        mockMvc.perform(get("/students/index.html"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("students/index"))
                .andExpect(model().attribute("students", hasSize(2)));  //two records in findAll() Set
    }

    @Test
    public void testGetStudentNotFound() throws Exception {

        when(studentService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"))
                .andExpect(model().attributeExists("exception"));
    }

    @Test
    void numberFormatStudentFindByIdTest() throws Exception{
        mockMvc.perform(get("/students/ITitURREhcjc"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"))
                .andExpect(model().attributeExists("exception"));
    }

    @Test
    void showStudentById() throws Exception{
        when(studentService.findById(anyLong())).thenReturn(testStudent);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("/students/studentDetails"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    void showStudentByIdCheckFirstName() throws Exception{
        when(studentService.findById(anyLong())).thenReturn(testStudent);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("/students/studentDetails"))
                .andExpect(model().attribute("student",
                        hasProperty("firstName", Matchers.is(testStudent.getFirstName()))));
    }

    @Test
    void showStudentByIdCheckTeacher() throws Exception{
        when(studentService.findById(anyLong())).thenReturn(testStudent);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("/students/studentDetails"))
                .andExpect(model().attribute("student",
                        hasProperty("teacher", Matchers.is(testStudent.getTeacher()))));
    }

    @Test
    void showStudentByIdCheckTeacherLastName(){
        when(studentService.findById(anyLong())).thenReturn(testStudent);

        assertEquals(teacher1.getLastName(), studentService.findById(23L).getTeacher().getLastName());
    }

    @Test
    void showStudentByIdCheckFormGroupName(){
        when(studentService.findById(anyLong())).thenReturn(testStudent);

        assertEquals(formGroupList1.getGroupName(), studentService.findById(23L).getFormGroupList().getGroupName());
    }

    @Test
    void showStudentByIdCheckEmail(){
        when(studentService.findById(anyLong())).thenReturn(testStudent);

        assertEquals(teacher1Contact.getEmail(), studentService.findById(23L).getContactDetail().getEmail());
    }

    @Test
    void showStudentByIdCheckContactDetail() throws Exception {
        when(studentService.findById(anyLong())).thenReturn(testStudent);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().is(200))
                .andExpect(status().isOk())
                .andExpect(view().name("/students/studentDetails"))
                .andExpect(model().attribute("student",
                        hasProperty("contactDetail", Matchers.is(teacher1Contact))));
    }

    @Test
    void showStudentByIdCheckNumberOfGuardians(){
        when(studentService.findById(anyLong())).thenReturn(testStudent);

        assertEquals(1, studentService.findById(23L).getGuardians().size());
    }

    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get("/students/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("/students/newStudent"))
                .andExpect(model().attributeExists("student"));
    }

//    @Test
//    void processCreationForm() throws Exception {
//        //todo re-test after form validation
//        when(studentService.save(ArgumentMatchers.any())).thenReturn(Student.builder().id(1L).build());
//
//        mockMvc.perform(post("/students/new"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/students/1"))
//                .andExpect(model().attributeExists("student"));
//
//        verify(studentService).save(ArgumentMatchers.any());
//    }

    @Test
    void initUpdateStudentForm() throws Exception {
        when(studentService.findById(anyLong())).thenReturn(Student.builder().id(1L).build());

        mockMvc.perform(get("/students/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("/students/updateStudent"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    void processUpdateStudentForm() throws Exception {
        when(studentService.save(any())).thenReturn(Student.builder().id(1L).build());
        when(studentService.findById(anyLong())).thenReturn(Student.builder().id(1L).build());

        mockMvc.perform(post("/students/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/students/1"))
                .andExpect(model().attributeExists("student"));

        verify(studentService).save(any());
        verify(studentService).findById(anyLong());
    }

    @Test
    void initUpdateTutorForm() throws Exception{
        when(studentService.findById(anyLong())).thenReturn(Student.builder().build());

        mockMvc.perform(get("/students/3/tutor/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("/teachers/newTeacher"))
                .andExpect(model().attributeExists("teacher"));
    }

    @Test
    void processUpdateTutorForm() throws Exception{
        when(studentService.findById(anyLong())).thenReturn(Student.builder().build());
        when(studentService.save(any())).thenReturn(Student.builder().id(3L).build());
        when(teacherService.save(any())).thenReturn(Teacher.builder().build());

        mockMvc.perform(post("/students/3/tutor/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/students/3/edit"));

        //teacherService does not always run if the teacher already exists on file
        verify(studentService).save(any());
    }

    @Test
    void initUpdateStudentSetForm_emptyStudentSet() throws Exception {
        when(guardianService.findById(anyLong())).thenReturn(Guardian.builder().students(new HashSet<>()).build());

        mockMvc.perform(get("/students/guardian/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("/guardians/updateStudentSet"))
                .andExpect(model().attributeExists("student1"))
                .andExpect(model().attributeExists("student2"))
                .andExpect(model().attributeExists("guardian"));
    }

    @Test
    void initUpdateStudentSetForm_oneStudentSet() throws Exception {
        when(guardianService.findById(anyLong())).thenReturn(Guardian.builder().students(studentSet_1).build());

        mockMvc.perform(get("/students/guardian/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("/guardians/updateStudentSet"))
                .andExpect(model().attributeExists("student1"))
                .andExpect(model().attributeExists("student2"))
                .andExpect(model().attributeExists("guardian"));
    }

    @Test
    void initUpdateStudentSetForm_twoStudentSet() throws Exception {
        when(guardianService.findById(anyLong())).thenReturn(Guardian.builder().students(studentSet_2).build());

        mockMvc.perform(get("/students/guardian/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("/guardians/updateStudentSet"))
                .andExpect(model().attributeExists("student1"))
                .andExpect(model().attributeExists("student2"))
                .andExpect(model().attributeExists("guardian"));
    }

    //no mock test for processStudentSetForm()
}