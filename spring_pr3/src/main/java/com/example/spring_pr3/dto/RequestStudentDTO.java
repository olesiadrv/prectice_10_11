package com.example.spring_pr3.dto;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class RequestStudentDTO {
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotNull(message = "Age is mandatory")
    @Min(value = 16, message = "Age must be at least 16")
    @Max(value = 100, message = "Age must be less than 100")
    private Integer age;
}
