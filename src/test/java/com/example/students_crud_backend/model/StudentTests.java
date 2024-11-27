package com.example.students_crud_backend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class StudentTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void testValidStudent() {
        Student student = new Student();
        student.setName("John Doe");
        student.setEmail("johndoe@example.com");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);
        assertTrue(violations.isEmpty(), "Expected no validation errors for valid input");
    }

    @Test
    void testInvalidStudentName() {
        Student student = new Student();
        student.setName(""); // Nome vazio
        student.setEmail("johndoe@example.com");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);
        assertFalse(violations.isEmpty(), "Expected validation errors for empty name");
    }

    @Test
    void testInvalidStudentEmail() {
        Student student = new Student();
        student.setName("John Doe");
        student.setEmail("invalid-email"); // Email inválido

        Set<ConstraintViolation<Student>> violations = validator.validate(student);
        assertFalse(violations.isEmpty(), "Expected validation errors for invalid email");
    }

    @Test
    void testNullStudentEmail() {
        Student student = new Student();
        student.setName("John Doe");
        student.setEmail(null); // Email nulo

        Set<ConstraintViolation<Student>> violations = validator.validate(student);
        assertFalse(violations.isEmpty(), "Expected validation errors for null email");
    }
}