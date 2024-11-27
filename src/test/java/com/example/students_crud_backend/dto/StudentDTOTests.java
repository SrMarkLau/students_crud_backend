package com.example.students_crud_backend.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class StudentDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void testValidStudentDTO() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Jane Doe");
        studentDTO.setEmail("janedoe@example.com");

        Set<ConstraintViolation<StudentDTO>> violations = validator.validate(studentDTO);
        assertTrue(violations.isEmpty(), "Expected no validation errors for valid input");
    }

    @Test
    void testInvalidStudentDTOName() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName(""); // Nome vazio
        studentDTO.setEmail("janedoe@example.com");

        Set<ConstraintViolation<StudentDTO>> violations = validator.validate(studentDTO);
        assertFalse(violations.isEmpty(), "Expected validation errors for empty name");
    }

    @Test
    void testInvalidStudentDTOEmail() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Jane Doe");
        studentDTO.setEmail("invalid-email"); // Email inválido

        Set<ConstraintViolation<StudentDTO>> violations = validator.validate(studentDTO);
        assertFalse(violations.isEmpty(), "Expected validation errors for invalid email");
    }

    @Test
    void testNullStudentDTOEmail() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Jane Doe");
        studentDTO.setEmail(null); // Email nulo

        Set<ConstraintViolation<StudentDTO>> violations = validator.validate(studentDTO);
        assertFalse(violations.isEmpty(), "Expected validation errors for null email");
    }
}
