package com.example.A2.domain.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    private Long id;

    private Long idCustomer;

    private Long idProduct;

    private String reviewText;

    private String createdAt;

    private Integer numberLikes;


}
