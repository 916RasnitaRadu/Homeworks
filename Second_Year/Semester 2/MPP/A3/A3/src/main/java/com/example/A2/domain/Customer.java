package com.example.A2.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;



@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Customer {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "customer_id") Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private Integer age;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Review> reviewList;

}
