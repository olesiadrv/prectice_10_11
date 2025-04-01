package com.example.spring_pr3.controller;

import com.example.spring_pr3.dto.ResponseStudentDTO;
import com.example.spring_pr3.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final StudentService studentService;

    @GetMapping
    public String home(Model model) {
        List<ResponseStudentDTO> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "home";
    }
}
