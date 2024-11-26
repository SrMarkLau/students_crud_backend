package com.example.students_crud_backend.controller;

import com.example.students_crud_backend.dto.StudentDTO;
import com.example.students_crud_backend.model.Student;
import com.example.students_crud_backend.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    private Student sampleStudent;
    private StudentDTO sampleStudentDTO;

    @BeforeEach
    void setUp() {
        sampleStudent = new Student();
        sampleStudent.setId(1L);
        sampleStudent.setName("John Doe");
        sampleStudent.setEmail("johndoe@example.com");

        sampleStudentDTO = new StudentDTO();
        sampleStudentDTO.setName("John Doe");
        sampleStudentDTO.setEmail("johndoe@example.com");
    }

    @Test
    void testGetAllStudents() throws Exception {
        Mockito.when(studentService.getAllStudents()).thenReturn(List.of(sampleStudent));

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sampleStudent.getId()))
                .andExpect(jsonPath("$[0].name").value(sampleStudent.getName()))
                .andExpect(jsonPath("$[0].email").value(sampleStudent.getEmail()));
    }

    @Test
    void testGetStudentById() throws Exception {
        Mockito.when(studentService.getStudentById(1L)).thenReturn(Optional.of(sampleStudent));

        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleStudent.getId()))
                .andExpect(jsonPath("$.name").value(sampleStudent.getName()))
                .andExpect(jsonPath("$.email").value(sampleStudent.getEmail()));
    }

    @Test
    void testGetStudentByIdNotFound() throws Exception {
        Mockito.when(studentService.getStudentById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateStudent() throws Exception {
        Mockito.when(studentService.createStudent(any(StudentDTO.class))).thenReturn(sampleStudent);

        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\", \"email\":\"johndoe@example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(sampleStudent.getId()))
                .andExpect(jsonPath("$.name").value(sampleStudent.getName()))
                .andExpect(jsonPath("$.email").value(sampleStudent.getEmail()));
    }

    @Test
    void testUpdateStudent() throws Exception {
        Mockito.when(studentService.updateStudent(eq(1L), any(StudentDTO.class))).thenReturn(Optional.of(sampleStudent));

        mockMvc.perform(put("/api/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Name\", \"email\":\"updatedemail@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleStudent.getId()))
                .andExpect(jsonPath("$.name").value(sampleStudent.getName()))
                .andExpect(jsonPath("$.email").value(sampleStudent.getEmail()));
    }

    @Test
    void testUpdateStudentNotFound() throws Exception {
        Mockito.when(studentService.updateStudent(eq(1L), any(StudentDTO.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Name\", \"email\":\"updatedemail@example.com\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteStudent() throws Exception {
        Mockito.when(studentService.deleteStudent(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/students/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteStudentNotFound() throws Exception {
        Mockito.when(studentService.deleteStudent(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/students/1"))
                .andExpect(status().isNotFound());
    }
}