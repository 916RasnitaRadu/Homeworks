package com.example.A1.repository;

import com.example.A1.domain.Student;

import javax.swing.text.html.Option;
import java.util.Map;
import java.util.Optional;

public interface IRepository {
    Optional<Student> get(Integer id);
    void add(Student obj);

    void delete(Integer id);
    void update(Student newStudent);
    Map<Integer,Student> getRepository();

    boolean findByID(Integer id);
}
