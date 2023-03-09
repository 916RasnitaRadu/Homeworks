package com.example.A1.service;

import com.example.A1.domain.Student;
import com.example.A1.repository.Repository;

import java.util.HashMap;
import java.util.Optional;


@org.springframework.stereotype.Service
public class Service {
    private Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public Optional<Student> getStudentService(Integer id) {
        if (repository.findByID(id)) return this.repository.get(id);
        return Optional.empty();
    }

    // creates a new Student object with the given parameters and adds them to the repo
    public void addStudent(Student newStudent)
    {
        //Student newStudent = new Student(id, name,bDay,email,gpa,group);
        this.repository.add(newStudent);
    }

    // gets all the students from the repository
    public HashMap<Integer, Student> getStudents() { return (HashMap<Integer, Student>) repository.getRepository();}

    // deletes a student based on id
    public void deleteStudent(Integer id)
    {
        this.repository.delete(id);
    }

    public void update(Student student)
    {
        if (repository.findByID(student.getId())) {
            this.repository.update(student);
        }
    }
}
