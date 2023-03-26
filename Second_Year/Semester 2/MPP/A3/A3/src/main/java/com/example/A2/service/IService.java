package com.example.A2.service;

import java.util.List;
import java.util.Optional;

public interface IService<T> {
    Optional<T> get(Long id);

    void addService(T newEntity);

    List<T> getAll();

    void deleteService(Long id);

    void updateService(Long id, T entity);
}
