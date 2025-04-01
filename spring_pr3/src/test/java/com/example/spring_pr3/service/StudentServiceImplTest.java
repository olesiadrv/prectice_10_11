package com.example.spring_pr3.service;

import com.example.spring_pr3.dto.RequestStudentDTO;
import com.example.spring_pr3.dto.ResponseStudentDTO;
import com.example.spring_pr3.model.Student;
import com.example.spring_pr3.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    private ModelMapper modelMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student testStudent;
    private RequestStudentDTO testRequestDTO;
    private ResponseStudentDTO testResponseDTO;
    private List<Student> testStudents;
    private List<ResponseStudentDTO> testResponseDTOs;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        studentService = new StudentServiceImpl(studentRepository, modelMapper);

        testStudent = new Student(1L, "Test Student", 20);
        testStudents = Arrays.asList(testStudent);

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
    void getAllStudents_ShouldReturnListOfStudents() {
        when(studentRepository.findAll()).thenReturn(testStudents);

        List<ResponseStudentDTO> result = studentService.getAllStudents();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testStudent.getName(), result.get(0).getName());
        assertEquals(testStudent.getAge(), result.get(0).getAge());
        verify(studentRepository).findAll();
    }

    @Test
    void getStudentById_WhenStudentExists_ShouldReturnStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));

        ResponseStudentDTO result = studentService.getStudentById(1L);

        assertNotNull(result);
        assertEquals(testStudent.getName(), result.getName());
        assertEquals(testStudent.getAge(), result.getAge());
        verify(studentRepository).findById(1L);
    }

    @Test
    void createStudent_ShouldReturnCreatedStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        ResponseStudentDTO result = studentService.createStudent(testRequestDTO);

        assertNotNull(result);
        assertEquals(testStudent.getName(), result.getName());
        assertEquals(testStudent.getAge(), result.getAge());
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void updateStudent_WhenStudentExists_ShouldReturnUpdatedStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        ResponseStudentDTO result = studentService.updateStudent(1L, testRequestDTO);

        assertNotNull(result);
        assertEquals(testStudent.getName(), result.getName());
        assertEquals(testStudent.getAge(), result.getAge());
        verify(studentRepository).findById(1L);
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void deleteStudent_ShouldCallRepositoryDelete() {
        doNothing().when(studentRepository).deleteById(1L);

        studentService.deleteStudent(1L);

        verify(studentRepository).deleteById(1L);
    }
}
