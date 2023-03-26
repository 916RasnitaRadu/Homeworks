package com.example.A2.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="review")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String reviewText;

    private String createdAt;

    private Integer numberLikes;

}
