package com.example.spring_pr3.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ResponseStudentDTO {
    private Long id;
    private String name;
    private Integer age;
    private LocalDateTime responseDate = LocalDateTime.now();
}