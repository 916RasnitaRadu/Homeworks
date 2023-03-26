package com.example.A2.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.List;


@Entity
@Table(name="product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Product {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "product_id") Long id;

    private String name;

    private double price;

    private String description;

    private String color;

    private String category;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Review> reviewsList;

}
