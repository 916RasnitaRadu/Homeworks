package com.example.A2.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ProductWithReviewDTO {
    private Long id;

    private String name;

    private double price;

    private String description;

    private String color;

    private String category;

    private List<ReviewRequest> reviewList;

}
