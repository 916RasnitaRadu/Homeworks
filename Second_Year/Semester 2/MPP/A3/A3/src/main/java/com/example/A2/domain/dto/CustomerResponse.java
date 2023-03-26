package com.example.A2.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class CustomerResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private Integer age;


}
