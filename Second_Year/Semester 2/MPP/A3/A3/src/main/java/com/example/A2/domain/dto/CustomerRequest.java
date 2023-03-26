package com.example.A2.domain.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private Integer age;
}
