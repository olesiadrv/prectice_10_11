package com.example.spring_pr3.controller;

import com.example.spring_pr3.dto.RequestStudentDTO;
import com.example.spring_pr3.dto.ResponseStudentDTO;
import com.example.spring_pr3.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentRestController.class)
class StudentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private RequestStudentDTO testRequestDTO;
    private ResponseStudentDTO testResponseDTO;
    private List<ResponseStudentDTO> testResponseDTOs;

    @BeforeEach
    void setUp() {
        testRequestDTO = new RequestStudentDTO();
        testRequestDTO.setName("Test Student");
        testRequestDTO.setAge(20);

        testResponseDTO = new ResponseStudentDTO();
        testResponseDTO.setId(1L);
        testResponseDTO.setName("Test Student");
        testResponseDTO.setAge(20);
        testResponseDTOs = Arrays.asList(testResponseDTO);
    }

    @Test
    void getAllStudents_ShouldReturnListOfStudents() throws Exception {
        when(studentService.getAllStudents()).thenReturn(testResponseDTOs);

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Student"))
                .andExpect(jsonPath("$[0].age").value(20));

        verify(studentService).getAllStudents();
    }

    @Test
    void getStudentById_WhenStudentExists_ShouldReturnStudent() throws Exception {
        when(studentService.getStudentById(1L)).thenReturn(testResponseDTO);

        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Student"))
                .andExpect(jsonPath("$.age").value(20));

        verify(studentService).getStudentById(1L);
    }

    @Test
    void createStudent_ShouldReturnCreatedStudent() throws Exception {
        when(studentService.createStudent(any(RequestStudentDTO.class))).thenReturn(testResponseDTO);

        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Student"))
                .andExpect(jsonPath("$.age").value(20));

        verify(studentService).createStudent(any(RequestStudentDTO.class));
    }

    @Test
    void updateStudent_WhenStudentExists_ShouldReturnUpdatedStudent() throws Exception {
        when(studentService.updateStudent(eq(1L), any(RequestStudentDTO.class))).thenReturn(testResponseDTO);

        mockMvc.perform(put("/api/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Student"))
                .andExpect(jsonPath("$.age").value(20));

        verify(studentService).updateStudent(eq(1L), any(RequestStudentDTO.class));
    }

    @Test
    void deleteStudent_ShouldReturnNoContent() throws Exception {
        doNothing().when(studentService).deleteStudent(1L);

        mockMvc.perform(delete("/api/students/1"))
                .andExpect(status().isNoContent());

        verify(studentService).deleteStudent(1L);
    }
}
