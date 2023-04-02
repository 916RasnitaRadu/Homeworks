package com.example.A2.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ReviewResponse {
    private Long id;

    private Long idCustomer;

    private Long idProduct;

    private String reviewText;

    private String createdAt;

    private Integer numberLikes;
}
