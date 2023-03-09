package com.example.A1.api;


import com.example.A1.service.Service;
import org.springframework.context.annotation.ComponentScan;


import com.example.A1.domain.Student;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@ComponentScan({"com.example.A1.service.Service"} )
@RestController
@RequiredArgsConstructor
public class StudentController {
    private final Service service;



    @PostMapping("/")
    public void addStudent(@RequestBody Student student) {
        service.addStudent(student);
    }

    @GetMapping("/{id}")
    public Optional<Student> getStudent(@PathVariable Integer id)
    {
        return this.service.getStudentService(id);
    }

    @GetMapping("/")
    public Map<Integer,Student> getStudents()
    {
        return this.service.getStudents();
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Integer id)
    {
        this.service.deleteStudent(id);
    }

    @PatchMapping("/{id}")
    public void updateStudent(@RequestBody Student student)
    {
        this.service.update(student);
    }
}
