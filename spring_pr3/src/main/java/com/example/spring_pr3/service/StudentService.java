package com.example.spring_pr3.service;

import com.example.spring_pr3.dto.RequestStudentDTO;
import com.example.spring_pr3.dto.ResponseStudentDTO;
import java.util.List;

public interface StudentService {
    List<ResponseStudentDTO> getAllStudents();
    ResponseStudentDTO getStudentById(Long id);
    ResponseStudentDTO createStudent(RequestStudentDTO studentDTO);
    ResponseStudentDTO updateStudent(Long id, RequestStudentDTO studentDTO);
    void deleteStudent(Long id);
}
