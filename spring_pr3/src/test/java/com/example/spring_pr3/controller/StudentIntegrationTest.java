package com.example.spring_pr3.controller;

import com.example.spring_pr3.model.Student;
import com.example.spring_pr3.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // відкат транзакції після кожного тесту
public class StudentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        // Очищуємо базу перед кожним тестом
        studentRepository.deleteAll();
        studentRepository.saveAll(List.of(
                new Student(null, "Іван", 20),
                new Student(null, "Марія", 19)
        ));
    }

    @Test
    void testGetAllStudents() throws Exception {
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Іван"))
                .andExpect(jsonPath("$[1].name").value("Марія"));
    }

    @Test
    void testCreateStudent() throws Exception {
        Student newStudent = new Student(null, "Олеся", 19);

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStudent)))
                .andExpect(status().isCreated());

        List<Student> students = studentRepository.findAll();
        assert students.size() == 3;
    }

    @Test
    void testGetStudentById() throws Exception {
        Long id = studentRepository.findAll().get(0).getId();

        mockMvc.perform(get("/api/students/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Іван"));
    }

    @Test
    void testUpdateStudent() throws Exception {
        Long id = studentRepository.findAll().get(0).getId();

        Student updatedStudent = new Student(null, "Іван (Оновлено)", 23);

        mockMvc.perform(put("/api/students/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedStudent)))
                .andExpect(status().isOk());

        Student student = studentRepository.findById(id).orElseThrow();
        assert student.getName().equals("Іван (Оновлено)");
    }

    @Test
    void testDeleteStudent() throws Exception {
        Long id = studentRepository.findAll().get(0).getId();

        mockMvc.perform(delete("/api/students/{id}", id))
                .andExpect(status().isNoContent());

        assert studentRepository.findById(id).isEmpty();
    }
}
